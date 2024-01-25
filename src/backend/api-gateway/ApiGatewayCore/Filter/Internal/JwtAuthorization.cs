using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;
using Microsoft.IdentityModel.Tokens;

namespace ApiGatewayCore.Filter;

internal class JwtAuthorization : IJwtAuthorization
{
    public void CreateToken(Adapter adapter, HttpContext context)
    {
        if (adapter.Authorization.jwtValidator == null)
            throw new Exception();

        List<Claim> claims = new List<Claim>()
        {
            new Claim(ClaimTypes.Name, "id")
        };
        string accessToken = CreateAccessToken(adapter.Authorization.jwtValidator, claims);
        string refreshToken = CreateRefreshToken();

        context.Response.Header["Authorization"] = accessToken;
        context.Response.Cookie.Append("refreshToken", refreshToken);
    }

    public ClaimsPrincipal? GetPrincipal(string accessToken, string secretKey)
    {
        var tokenValidationParameters = new TokenValidationParameters
        {
            ValidateAudience = false,
            ValidateIssuer = false,
            ValidateIssuerSigningKey = true,
            IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(secretKey)),
            ValidateLifetime = false
        };

        var tokenHandler = new JwtSecurityTokenHandler();
        var principal = tokenHandler.ValidateToken(accessToken, tokenValidationParameters, out SecurityToken securityToken);
        if (securityToken is not JwtSecurityToken jwtSecurityToken || !jwtSecurityToken.Header.Alg.Equals(SecurityAlgorithms.HmacSha256, StringComparison.InvariantCultureIgnoreCase))
            throw new SecurityTokenException("Invalid token");

        return principal;
    }

    public string RefreshAccessToken(string refreshToken)
    {
        // if (refreshToken == null)
        //     throw new Exception();

        // var tokenPrincipal = GetPrincipalFromToken(accessToken);
        // if (tokenPrincipal == null)
        //     throw new Exception();

        // string? id = tokenPrincipal.Identity?.Name;
        // if (id == null)
        //     throw new Exception();

        // string? refreshToken = _redis.GetRefreshTokenById(id);
        // if (refreshToken == null)
        //     throw new Exception();

        // if (refreshToken != refreshToken)
        //     throw new Exception();

        // string accessToken = CreateAccessToken(tokenPrincipal.Claims.ToList());
        // if (!_redis.InsertIdAndRefreshToken(
        //     id, refreshToken,
        //     new TimeSpan(0, 0, int.Parse(_config["JWT:RefreshTokenValidityInSecond"]))))
        //     throw new RedisException("Redis Repository Insert Error");

        // return accessToken;

        return "";
    }

    public bool ValidationToken(string accessToken)
    {
        // string[] tokens = token.AccessToken.Split(" ");
        // token.AccessToken = tokens[1];

        // if (tokens[0] != "Bearer")
        //     throw new Exception();

        // if (_redis.GetBlackListToken(token.AccessToken) != null)
        //     throw new Exception();

        // if (token.RefreshToken == null)
        //     throw new Exception();

        // // AccessToken이 무조건 있다고 가정
        // var tokenValidationParameters = new TokenValidationParameters
        // {
        //     ValidateAudience = true,
        //     ValidAudience = _config["JWT:ValidAudience"],
        //     ValidateIssuer = true,
        //     ValidIssuer = _config["JWT:ValidIssuer"],
        //     ValidateIssuerSigningKey = true,
        //     IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["JWT:Secret"])),
        //     ValidateLifetime = true,
        // };
        // var tokenHandler = new JwtSecurityTokenHandler();
        // try
        // {
        //     var principal = tokenHandler.ValidateToken(token.AccessToken, tokenValidationParameters, out SecurityToken securityToken);
        //     if (principal == null)
        //         throw new Exception();

        //     string id = principal.Identity?.Name ?? throw new Exception();

        //     return id;
        // }
        // catch (SecurityTokenExpiredException)
        // {
        //     RefreshToken(token);

        //     // 토큰 유효성 검사 실패
        //     throw new TokenException(4105, token);
        // }

        return true;
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