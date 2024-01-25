using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;
using Microsoft.IdentityModel.Tokens;

namespace ApiGatewayCore.Filter.Listner;

internal class AuthorizationFilter : DefaultFilter
{
    // 생성 관리 벨리데이션 체크깔쥐 다 해주기
    public Authorization Config { get; set; } = null!;
    public Dictionary<string, bool> IsAuthorization { get; set; } = new Dictionary<string, bool>();
    
    private string _secretKey = null!;
    private string _issuer = null!;
    private string _audience = null!;

    protected override void Working(Adapter adapter, HttpContext context)
    {
        // 해당 서비스가 Authentication Filter가 필요한 서비스인지 확인 필요
        if (config.Authorization != null)
        {
            // if(config.Authorization.jwtValidator.)
        }    
        
        // 토큰이 없을 경우        
        if (context.Request.Header.TryGetValue("Authorization", out string? token))
        {
            if (token == null)
                throw new Exception();

            // if (!ValidationToken(token))
                // throw new Exception();
        }
        else
        {

        }
    }
    protected override void Worked(Adapter adapter, HttpContext context)
    {
        if (config.Type == "JWT")
        {
            if (context.Request.Path == config.Authorization.jw)
            {
                if (context.Response.StatusCode == 200)
                {
                    // 토큰 발급

                }
            }
        }
    }

    private ClaimsPrincipal? GetPrincipal(string token)
    {
        var tokenValidationParameters = new TokenValidationParameters
        {
            ValidateAudience = false,
            ValidateIssuer = false,
            ValidateIssuerSigningKey = true,
            IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("123124123123")),
            ValidateLifetime = false
        };

        var tokenHandler = new JwtSecurityTokenHandler();
        var principal = tokenHandler.ValidateToken(token, tokenValidationParameters, out SecurityToken securityToken);
        if (securityToken is not JwtSecurityToken jwtSecurityToken || !jwtSecurityToken.Header.Alg.Equals(SecurityAlgorithms.HmacSha256, StringComparison.InvariantCultureIgnoreCase))
            throw new SecurityTokenException("Invalid token");

        return principal;
    }

    private string RefreshToken(string? accessToken, string? refreshToken)
    {
        // 흐름도
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

    public void DeleteToken(string accessToken)
    {
        // string id = GetIdWithAccessToken(accessToken);

        // // 토큰 삭제 필요함
        // if (_redis.DeleteRefreshToken(id) == false)
        //     throw new Exception();

        // if (_redis.InsertBlackListToken(
        //             accessToken, id.ToString(),
        //             new TimeSpan(0, 0, int.Parse(_config["JWT:AccessTokenValidityInSecond"]))) == false)
        //     throw new Exception();
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
            // var authSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["JWT:Secret"]));
            // int.TryParse(_config["JWT:AccessTokenValidityInSecond"], out int tokenValidityInSecond);

            // var token = new JwtSecurityToken(
            //     issuer: _config["JWT:ValidIssuer"],
            //     audience: _config["JWT:ValidAudience"],
            //     expires: DateTime.Now.AddSeconds(tokenValidityInSecond),
            //     claims: authClaims,
            //     signingCredentials: new SigningCredentials(authSigningKey, SecurityAlgorithms.HmacSha256)
            //     );

            // return new JwtSecurityTokenHandler().WriteToken(token);

            return "";
        }
        catch (Exception)
        {
            throw new Exception();
        }
    }

    public string ValidationToken(string token)
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

        return "";
    }

    private string GetIdWithAccessToken(string accessToken)
    {
        var tokenPrincipal = GetPrincipal(accessToken);
        if (tokenPrincipal == null)
            throw new Exception();

        string? id = tokenPrincipal.Identity?.Name;
        if (id == null)
            throw new Exception();

        return id;
    }
}