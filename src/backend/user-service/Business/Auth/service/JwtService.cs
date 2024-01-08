using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;
using Microsoft.IdentityModel.Tokens;
using user_service.auth.dto;
using user_service.auth.repository;

namespace user_service
{
    namespace auth
    {
        namespace service
        {
            public class JwtService
            {
                private readonly IConfiguration _config;
                private readonly JwtRepository _jwtRepository;
                public JwtService(IConfiguration config, JwtRepository jwtRepository)
                {
                    _config = config;
                    _jwtRepository = jwtRepository;
                }

                public void RefreshToken(JwtDTO dto)
                {
                    // 흐름도
                    
                    // 1. Refresh Token 검증
                    // _jwtRepository.GetJwtById(dto.);

                    // 2. Refresh Token 검증 실패 시 예외 처리
                    // 3. Refresh Token 검증 성공 시 Access Token 재발급
                    // 4. Access Token 재발급 실패 시 예외 처리
                    // 5. Access Token 재발급 성공 시 Access Token 반환
                }
                public JwtSecurityToken CreateToken(List<Claim> authClaims)
                {
                    var authSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["JWT:Secret"]));
                    _ = int.TryParse(_config["JWT:TokenValidityInMinutes"], out int tokenValidityInMinutes);

                    var token = new JwtSecurityToken(
                        issuer: _config["JWT:ValidIssuer"],
                        audience: _config["JWT:ValidAudience"],
                        expires: DateTime.Now.AddMinutes(Double.Parse(_config["JWT:TokenValidityInMinutes"])),
                        claims: authClaims,
                        signingCredentials: new SigningCredentials(authSigningKey, SecurityAlgorithms.HmacSha256)
                        );

                    return token;
                }

                public string GenerateRefreshToken()
                {
                    var randomNumber = new byte[64];
                    using var rng = RandomNumberGenerator.Create();
                    rng.GetBytes(randomNumber);
                    return Convert.ToBase64String(randomNumber);
                }

                public bool ValidationToken(string accessToken)
                {
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
                    }
                    catch (Exception e)
                    {
                        // 예외 처리 로직 추가
                        System.Console.WriteLine(e.Message);
                    }
                    
                    return false;
                }
            }
        }
    }
}