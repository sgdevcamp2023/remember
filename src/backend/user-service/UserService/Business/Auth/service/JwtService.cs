using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;
using Microsoft.IdentityModel.Tokens;
using user_service.auth.dto;
using user_service.auth.entity;
using user_service.auth.exception;
using user_service.auth.repository;
using user_service.common;
using user_service.common.exception;
using user_service.logger;

namespace user_service
{
    namespace auth
    {
        namespace service
        {
            public class JwtService
            {
                private IConfiguration _config;
                private IAuthRedisRepository _redis;
                private IUserRepository _userRepository;
                private IBaseLogger _logger;
                public JwtService(IConfiguration config,
                                IAuthRedisRepository redisRepository,
                                IUserRepository userRepository,
                                IBaseLogger logger)
                {
                    _config = config;
                    _redis = redisRepository;
                    _userRepository = userRepository;
                    _logger = logger;
                }

                public string RefreshToken(TokenDTO dto)
                {
                    // 흐름도
                    if (dto.RefreshToken == null)
                        throw new ServiceException(4104);

                    var tokenPrincipal = GetPrincipalFromToken(dto.AccessToken);
                    if (tokenPrincipal == null)
                        throw new ServiceException(4101);

                    string? id = tokenPrincipal.Identity?.Name;
                    if (id == null)
                        throw new ServiceException(4101);

                    string? refreshToken = _redis.GetRefreshTokenById(id);
                    if (refreshToken == null)
                        throw new ServiceException(4101);

                    if (refreshToken != dto.RefreshToken)
                        throw new ServiceException(4101);

                    string accessToken = CreateAccessToken(tokenPrincipal.Claims.ToList());
                    if (!_redis.InsertIdAndRefreshToken(
                        id, refreshToken,
                        new TimeSpan(0, 0, int.Parse(_config["JWT:RefreshTokenValidityInSecond"]))))
                        throw new RedisException("Redis Repository Insert Error");

                    return accessToken;
                }
                
                public void DeleteToken(string id, string accessToken)
                {
                    // 토큰 삭제 필요함
                    if(_redis.DeleteRefreshToken(id) == false)
                        throw new ServiceException(4014);
                    

                    if(_redis.InsertBlackListToken(
                                accessToken, id.ToString(),
                                new TimeSpan(0, 0, int.Parse(_config["JWT:AccessTokenValidityInSecond"]))) == false)
                        throw new ServiceException(4014);
                }

                public TokenDTO CreateToken(LoginDTO loginDto)
                {
                    // 흐름도

                    // DB 체크
                    UserModel? user = _userRepository.GetUserByEmail(loginDto.Email);
                    if (user == null)
                        throw new ServiceException(4007);

                    loginDto.Password = Utils.SHA256Hash(loginDto.Password);
                    if (user.Password != loginDto.Password)
                        throw new ServiceException(4008);

                    // 1. Claim 생성
                    List<Claim> authClaims = new List<Claim>
                    {
                        new Claim(ClaimTypes.Name, user.Id.ToString()),
                        new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                    };

                    // 2. Access Token 생성
                    string accessToken = CreateAccessToken(authClaims);

                    // 3. Refresh Token 생성
                    string refreshToken = CreateRefreshToken();

                    // 4. Redis에 Refresh Token 저장
                    if (!_redis.InsertIdAndRefreshToken(
                        user.Id.ToString(), refreshToken,
                        new TimeSpan(0, 0, int.Parse(_config["JWT:RefreshTokenValidityInSecond"]))))
                        throw new RedisException("Redis Repository Insert Error");

                    return new TokenDTO(accessToken, refreshToken);
                }

                private string CreateRefreshToken()
                {
                    var randomNumber = new byte[64];
                    using var rng = RandomNumberGenerator.Create();
                    rng.GetBytes(randomNumber);
                    string RefreshToken = Convert.ToBase64String(randomNumber);

                    return RefreshToken;
                }

                private string CreateAccessToken(List<Claim> authClaims)
                {
                    try
                    {
                        var authSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["JWT:Secret"]));
                        int.TryParse(_config["JWT:AccessTokenValidityInSecond"], out int tokenValidityInSecond);

                        var token = new JwtSecurityToken(
                            issuer: _config["JWT:ValidIssuer"],
                            audience: _config["JWT:ValidAudience"],
                            expires: DateTime.Now.AddSeconds(tokenValidityInSecond),
                            claims: authClaims,
                            signingCredentials: new SigningCredentials(authSigningKey, SecurityAlgorithms.HmacSha256)
                            );

                        return new JwtSecurityTokenHandler().WriteToken(token);
                    }
                    catch (Exception)
                    {
                        throw new ServiceException(4102);
                    }
                }

                public string ValidationToken(TokenDTO token)
                {
                    string[] tokens = token.AccessToken.Split(" ");
                    token.AccessToken = tokens[1];

                    if (tokens[0] != "Bearer")
                        throw new ServiceException(4103);

                    if(_redis.GetBlackListToken(token.AccessToken) != null)
                        throw new ServiceException(4014);

                    if (token.RefreshToken == null)
                        throw new ServiceException(4104);
                        
                    // AccessToken이 무조건 있다고 가정
                    var tokenValidationParameters = new TokenValidationParameters
                    {
                        ValidateAudience = true,
                        ValidAudience = _config["JWT:ValidAudience"],
                        ValidateIssuer = true,
                        ValidIssuer = _config["JWT:ValidIssuer"],
                        ValidateIssuerSigningKey = true,
                        IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["JWT:Secret"])),
                        ValidateLifetime = true,
                    };
                    var tokenHandler = new JwtSecurityTokenHandler();
                    try
                    {
                        var principal = tokenHandler.ValidateToken(token.AccessToken, tokenValidationParameters, out SecurityToken securityToken);
                        if(principal == null)
                            throw new ServiceException(4100);

                        string id = principal.Identity?.Name ?? throw new ServiceException(4100);

                        return id;
                    }
                    catch (SecurityTokenExpiredException)
                    {
                        RefreshToken(token);

                        // 토큰 유효성 검사 실패
                        throw new TokenException(4105, token);
                    }
                }

                private ClaimsPrincipal? GetPrincipalFromToken(string? token)
                {
                    var tokenValidationParameters = new TokenValidationParameters
                    {
                        ValidateAudience = false,
                        ValidateIssuer = false,
                        ValidateIssuerSigningKey = true,
                        IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["JWT:Secret"])),
                        ValidateLifetime = false
                    };

                    var tokenHandler = new JwtSecurityTokenHandler();
                    var principal = tokenHandler.ValidateToken(token, tokenValidationParameters, out SecurityToken securityToken);
                    if (securityToken is not JwtSecurityToken jwtSecurityToken || !jwtSecurityToken.Header.Alg.Equals(SecurityAlgorithms.HmacSha256, StringComparison.InvariantCultureIgnoreCase))
                        throw new SecurityTokenException("Invalid token");

                    return principal;
                }
            }
        }
    }
}