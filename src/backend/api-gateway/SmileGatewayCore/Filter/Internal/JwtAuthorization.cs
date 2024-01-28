using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using Microsoft.IdentityModel.Tokens;
using SmileGatewayCore.Config;

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
            throw new Exception();

        // Claim 생성
        List<Claim> claims = new List<Claim>();
        
        JwtClaimsModel? claimsModel = JsonSerializer.Deserialize<JwtClaimsModel>(body);
        if (claimsModel == null)
            throw new Exception();
        
        claims.Add(new Claim(ClaimTypes.Name, claimsModel.Name));

        // 토큰 생성
        string accessToken = CreateAccessToken(adapter.Authorization.jwtValidator, claims);
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
            throw new Exception();

        string[] tokens = accessToken.Split(" ");
        accessToken = tokens[1];

        string? id = GetPrincipal(accessToken)?.Identity?.Name;
        if(id == null)
            throw new Exception();

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
            throw new Exception();

        string? name = tokenPrincipal.Identity?.Name;
        if (name == null)
            throw new Exception();

        string? refreshToken = _redis.GetByKey(name);
        if (refreshToken == null)
            throw new Exception();

        if (refreshToken != token.RefreshToken)
            throw new Exception();

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
            throw new Exception();

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
                throw new Exception();

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
        catch (Exception)
        {
            throw new Exception();
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