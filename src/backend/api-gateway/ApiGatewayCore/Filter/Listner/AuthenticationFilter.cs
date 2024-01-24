using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;
using Microsoft.IdentityModel.Tokens;

namespace ApiGatewayCore.Filter.Listner;

public class AuthenticationFilter : DefaultFilter
{
    // 생성 관리 벨리데이션 체크깔쥐 다 해주기
    private Authorization config = new Authorization();
    public override void Working(HttpContext context)
    {
        // 해당 서비스가 Authentication Filter가 필요한 서비스인지 확인 필요

        // 토큰이 없을 경우        
        if (context.Request.Header.TryGetValue("Authorization", out string? token))
        {
            if (token == null)
                throw new Exception();

            if (!ValidationToken(token))
                throw new Exception();
        }
    }
    public override void Worked(HttpContext context)
    {
        if (config.Type == "JWT")
        {
            if (context.Request.Path == config.jwtValidator!.Path)
            {
                // context.Response.
            }
        }
    }

    private bool ValidationToken(string token)
    {
        // AccessToken이 무조건 있다고 가정
        var tokenValidationParameters = new TokenValidationParameters
        {
            ValidateAudience = true,
            ValidAudience = config.jwtValidator!.ValidAudience,
            ValidateIssuer = true,
            ValidIssuer = config.jwtValidator!.ValidIssuser,
            ValidateIssuerSigningKey = true,
            IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(config.jwtValidator!.SecretKey)),
            ValidateLifetime = true,
        };
        var tokenHandler = new JwtSecurityTokenHandler();
        try
        {
            var principal = tokenHandler.ValidateToken(token, tokenValidationParameters, out SecurityToken securityToken);
            // if (principal == null)
                // throw new ServiceException(4100);

            // string id = principal.Identity?.Name ?? throw new ServiceException(4100);

            return true;
        }
        catch (SecurityTokenExpiredException)
        {
            // RefreshToken(token);

            // 토큰 유효성 검사 실패
            // throw new TokenException(4105, token);
            return false;
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
}