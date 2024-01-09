using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;
using Microsoft.IdentityModel.Tokens;
using user_service.auth.dto;
using user_service.auth.entity;
using user_service.auth.repository;
using user_service.exception;
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
                private IRedisRepository _redisRepository;
                private IBaseLogger _logger;
                public JwtService(IConfiguration config, RedisRepository redisRepository, FileLogger logger)
                {
                    _config = config;
                    _redisRepository = redisRepository;
                    _logger = logger;
                }

                public async Task<String> RefreshToken(JwtDTO dto)
                {
                    // 흐름도

                    // 1. Refresh Token
                    string? accressToken = await _redisRepository.GetJwtById(dto.RefreshToken);
                    if (accressToken == null)
                        throw new CustomException(4101);

                    // 2. Refresh Token 검증
                    if(dto.AccessToken != accressToken)
                        throw new CustomException(4101);
                    
                    // 3. Access Token에서 Claim 추출
                    List<Claim> authClaims = GetClaimsFromAccessToken(dto.AccessToken);

                    // 5. Access Token 재발급 성공 시 Access Token 반환
                    string accessToken = CreateAccessToken(authClaims);                    
                    _redisRepository.InsertRedis(
                        new RedisModel(dto.RefreshToken, accessToken), 
                        new TimeSpan(0, 0, int.Parse(_config["JWT:RefreshTokenInSecond"])));
                    
                    return accessToken;
                }
                public JwtDTO CreateToken(List<Claim> authClaims)
                {
                    string accessToken = CreateAccessToken(authClaims);
                    string refreshToken = CreateRefreshToken();

                    _redisRepository.InsertRedis(
                        new RedisModel(refreshToken, accessToken), 
                        new TimeSpan(0, 0, int.Parse(_config["JWT:RefreshTokenInSecond"])));

                    return new JwtDTO(accessToken, refreshToken);
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

                private List<Claim> GetClaimsFromAccessToken(string accessToken)
                {
                    var handler = new JwtSecurityTokenHandler();
                    var jwtToken = handler.ReadJwtToken(accessToken);

                    return jwtToken.Claims.ToList();
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
                    }
                    catch (Exception e)
                    {
                        // 예외 처리 로직 추가
                        _logger.Log(e.Message);
                        
                        throw new CustomException(4100);
                        // return false; or this
                    }

                    return true;
                }
            }
        }
    }
}