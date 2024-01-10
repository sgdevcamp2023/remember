using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;
using Microsoft.IdentityModel.Tokens;
using user_service.auth.dto;
using user_service.auth.entity;
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

                public async Task<String> RefreshToken(TokenDTO dto)
                {
                    // 흐름도

                    // 1. Refresh Token
                    string? accressToken = await _redisRepository.GetStringById(dto.RefreshToken);
                    if (accressToken == null)
                        throw new ServiceException(4101);

                    // 2. Refresh Token 검증
                    if (dto.AccessToken != accressToken)
                        throw new ServiceException(4101);

                    // 3. Access Token에서 Claim 추출
                    List<Claim> authClaims = GetClaimsFromAccessToken(dto.AccessToken);

                    // 5. Access Token 재발급 성공 시 Access Token 반환
                    string accessToken = CreateAccessToken(authClaims);
                    if (!_redisRepository.InsertRedis(
                        new RedisModel(dto.RefreshToken, accessToken),
                        new TimeSpan(0, 0, int.Parse(_config["JWT:RefreshTokenInSecond"]))))
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
                    if(user == null)
                        throw new ServiceException(4007);
                    
                    if(user.Password != loginDto.Password)
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
                        new RedisModel(refreshToken, accessToken),
                        new TimeSpan(0, 0, int.Parse(_config["JWT:RefreshTokenInSecond"]))))
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
                        int.TryParse(_config["JWT:TokenValidityInMinutes"], out int tokenValidityInMinutes);

                        var token = new JwtSecurityToken(
                            issuer: _config["JWT:ValidIssuer"],
                            audience: _config["JWT:ValidAudience"],
                            expires: DateTime.Now.AddSeconds(tokenValidityInMinutes),
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

                private List<Claim> GetClaimsFromAccessToken(string accessToken)
                {
                    try
                    {
                        var handler = new JwtSecurityTokenHandler();
                        var jwtToken = handler.ReadJwtToken(accessToken);

                        return jwtToken.Claims.ToList();
                    }
                    catch (Exception e)
                    {
                        _logger.Log(e.Message);
                        throw new ServiceException(4100);
                    }
                }
                public bool ValidationToken(string accessToken)
                {
                    // AccessToken이 무조건 있다고 가정
                    var tokenValidationParameters = new TokenValidationParameters
                    {
                        ValidateAudience = true,
                        ValidAudience = _config["JWT:ValidAudience"],
                        ValidateIssuer = true,
                        ValidIssuer = _config["JWT:ValidIssuer"],
                        ValidateIssuerSigningKey = true,
                        IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["JWT:Secret"])),
                        ValidateLifetime = true
                    };
                    var tokenHandler = new JwtSecurityTokenHandler();
                    try
                    {
                        tokenHandler.ValidateToken(accessToken, tokenValidationParameters, out SecurityToken securityToken);
                        // 토큰 유효성 검사 성공

                        // DB에서 고유 아이디 가져오는 코드 작성

                        return true;
                    }
                    catch (Exception e)
                    {
                        // 예외 처리 로직 추가
                        _logger.Log(e.Message);

                        throw new ServiceException(4100);
                    }
                }
            }
        }
    }
}