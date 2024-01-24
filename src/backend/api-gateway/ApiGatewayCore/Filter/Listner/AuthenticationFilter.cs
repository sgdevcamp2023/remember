using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;

namespace ApiGatewayCore.Filter.Listner;

public class AuthenticationFilter : DefaultFilter
{
    // 생성 관리 벨리데이션 체크깔쥐 다 해주기
    private Authorization config = new Authorization();
    public override void Working(HttpContext context)
    {
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
        if(context.Request.Path == config.jwtValidator.Path)
        {
            
        }
    }

    private bool ValidationToken(string token)
    {
        return true;
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