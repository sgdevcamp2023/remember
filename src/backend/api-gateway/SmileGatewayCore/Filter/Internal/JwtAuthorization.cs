using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using Microsoft.IdentityModel.Tokens;
using SmileGatewayCore.Config;
using SmileGatewayCore.Exception;

namespace SmileGatewayCore.Filter.Internal;

internal class JwtAuthorization : IJwtAuthorization
{
    private IRedisRepository _redis;
    public JwtAuthorization()
    {
        _redis = new RedisRepository("localhost:6379");
    }

    public JwtModel CreateToken(Adapter adapter, string body)
    {
        if (adapter.Authorization!.jwtValidator == null)
            throw new ConfigException(3100);

        // Claim 생성
        JwtClaimsModel? claimsModel = Newtonsoft.Json.JsonConvert.DeserializeObject<JwtClaimsModel>(body);
        if (claimsModel == null)
            throw new AuthException(3007);
        
        List<Claim> claims = new List<Claim>{
            new Claim(ClaimTypes.Name, claimsModel.Name),
        };

        // 토큰 생성
        string accessToken = "Bearer " + CreateAccessToken(adapter.Authorization.jwtValidator, claims);
        string refreshToken = CreateRefreshToken();
        
        // 레디스에 토큰 저장
        _redis.Insert(claimsModel.Name, refreshToken);

        // 리턴
        return new JwtModel(accessToken, refreshToken);
    }

    public void DeleteToken(Adapter adapter, HttpContext context)
    {
        context.Request.Header.TryGetValue("Authorization", out string? accessToken);
        if (accessToken == null)
            throw new AuthException(3005);

        string[] tokens = accessToken.Split(" ");
        accessToken = tokens[1];

        string? id = GetPrincipal(accessToken)?.Identity?.Name;
        if(id == null)
            throw new AuthException(3000);

        _redis.Delete(id);
    }

    public ClaimsPrincipal? GetPrincipal(string accessToken)
    {
        var tokenValidationParameters = new TokenValidationParameters
        {
            ValidateAudience = false,
            ValidateIssuer = false,
            ValidateLifetime = false
        };

        var tokenHandler = new JwtSecurityTokenHandler();
        var principal = tokenHandler.ValidateToken(accessToken, tokenValidationParameters, out SecurityToken securityToken);
        if (securityToken is not JwtSecurityToken jwtSecurityToken || !jwtSecurityToken.Header.Alg.Equals(SecurityAlgorithms.HmacSha256, StringComparison.InvariantCultureIgnoreCase))
            throw new SecurityTokenException("Invalid token");

        return principal;
    }

    public JwtModel RefreshAccessToken(JwtValidator validator, JwtModel token)
    {
        // 엑세스 토큰 Claim 가져오기
        var tokenPrincipal = GetPrincipal(token.AccessToken);
        if (tokenPrincipal == null)
            throw new AuthException(3000);

        string? name = tokenPrincipal.Identity?.Name;
        if (name == null)
            throw new AuthException(3000);

        string? refreshToken = _redis.GetByKey(name);
        if (refreshToken == null)
            throw new AuthException(3008);

        if (refreshToken != token.RefreshToken)
            throw new AuthException(3001);

        string accessToken = CreateAccessToken(validator, tokenPrincipal.Claims.ToList());
        refreshToken = CreateRefreshToken();

        _redis.Insert(
            name, refreshToken,
            new TimeSpan(0, 0, validator.RefreshTokenValidityInSecond));

        return new JwtModel(accessToken, refreshToken);
    }

    // 성공이냐?
    // 타임 아웃인지
    // 토큰이 JWT가 맞는지
    public bool ValidationToken(JwtValidator validator, string accessToken)
    {
        string[] tokens = accessToken.Split(" ");
        accessToken = tokens[1];

        if (tokens[0] != "Bearer")
            throw new AuthException(3003);

        var tokenValidationParameters = new TokenValidationParameters
        {
            ValidateAudience = true,
            ValidAudience = validator.ValidAudience,
            ValidateIssuer = true,
            ValidIssuer = validator.ValidIssuer,
            ValidateIssuerSigningKey = true,
            IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(validator.SecretKey)),
            ValidateLifetime = true,
        };
        var tokenHandler = new JwtSecurityTokenHandler();
        try
        {
            var principal = tokenHandler.ValidateToken(accessToken, tokenValidationParameters, out SecurityToken securityToken);
            if (principal == null)
                throw new AuthException(3000);

            return true;
        }
        catch (SecurityTokenExpiredException)
        {
            return false;
        }
    }

    private string CreateAccessToken(JwtValidator validator, List<Claim> claims)
    {
        try
        {
            var authSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(validator.SecretKey));

            var token = new JwtSecurityToken(
                issuer: validator.ValidIssuer,
                audience: validator.ValidAudience,
                expires: DateTime.Now.AddSeconds(validator.AccessTokenValidityInSecond),
                claims: claims,
                signingCredentials: new SigningCredentials(authSigningKey, SecurityAlgorithms.HmacSha256)
                );

            return new JwtSecurityTokenHandler().WriteToken(token);

        }
        catch (System.Exception)
        {
            throw new AuthException(3002);
        }
    }

    private string CreateRefreshToken()
    {
        var randomNumber = new byte[64];
        using var rng = RandomNumberGenerator.Create();
        rng.GetBytes(randomNumber);
        string RefreshToken = Convert.ToBase64String(randomNumber);

        return RefreshToken;
    }
}