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
                private IStringRedisRepository _redisRepository;
                private IUserRepository _userRepository;
                private IBaseLogger _logger;
                public JwtService(IConfiguration config,
                                IStringRedisRepository redisRepository,
                                IUserRepository userRepository,
                                IBaseLogger logger)
                {
                    _config = config;
                    _redisRepository = redisRepository;
                    _userRepository = userRepository;
                    _logger = logger;
                }

                public string RefreshToken(TokenDTO dto)
                {
                    // 흐름도
                    if (dto.RefreshToken == null)
                        throw new ServiceException(4104);

                    var tokenPrincipal = GetPrincipalFromExpiredToken(dto.AccessToken);
                    if (tokenPrincipal == null)
                        throw new ServiceException(4101);

                    string? id = tokenPrincipal.Identity?.Name;
                    if (id == null)
                        throw new ServiceException(4101);

                    string? refreshToken = _redisRepository.GetStringById(id);
                    if (refreshToken == null)
                        throw new ServiceException(4101);

                    if (refreshToken != dto.RefreshToken)
                        throw new ServiceException(4101);

                    string accessToken = CreateAccessToken(tokenPrincipal.Claims.ToList());
                    if (!_redisRepository.InsertRedis(
                        new RedisModel(id, refreshToken),
                        new TimeSpan(0, 0, int.Parse(_config["JWT:RefreshTokenValidityInSecond"]))))
                        throw new RedisException("Redis Repository Insert Error");

                    return accessToken;
                }
                public void DeleteToken()
                {
                    // 토큰 삭제 필요함
                }
                public TokenDTO CreateToken(LoginDTO loginDto)
                {
                    // 흐름도

                    // DB 체크
                    UserModel? user = _userRepository.GetUserByEmail(loginDto.Email);
                    if (user == null)
                        throw new ServiceException(4007);

                    loginDto.Password = SHA356Hash(loginDto.Password);
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
                    if (!_redisRepository.InsertRedis(
                        new RedisModel(user.Id.ToString(), refreshToken),
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
                    catch (Exception e)
                    {
                        _logger.Log(e.Message);
                        throw new ServiceException(4102);
                    }
                }

                public ClaimsPrincipal? GetPrincipalFromExpiredToken(string? token)
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


                public long ValidationToken(TokenDTO token)
                {
                    if (token.RefreshToken == null)
                        throw new ServiceException(4104);

                    string[] tokens = token.AccessToken.Split(" ");
                    if (tokens[0] != "Bearer")
                        throw new ServiceException(4103);

                    token.AccessToken = tokens[1];
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

                        // 토큰 유효성 검사 성공
                        var claims = principal.Claims.ToList();

                        foreach (var claim in claims)
                        {
                            if (claim.Type == ClaimTypes.Name)
                                return long.Parse(claim.Value);
                        }

                        throw new ServiceException(4100);
                    }
                    catch (SecurityTokenExpiredException)
                    {
                        RefreshToken(token);

                        // 토큰 유효성 검사 실패
                        throw new TokenException(4105, token);
                    }
                    catch (Exception e)
                    {
                        // 예외 처리 로직 추가
                        _logger.Log(e.Message);

                        throw new ServiceException(4100);
                    }
                }

                private string SHA356Hash(string password)
                {
                    SHA256 sha256Hash = SHA256.Create();
                    byte[] data = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(password));
                    StringBuilder sBuilder = new StringBuilder();
                    for (int i = 0; i < data.Length; i++)
                    {
                        sBuilder.Append(data[i].ToString("x2"));
                    }
                    return sBuilder.ToString();
                }
            }
        }
    }
}