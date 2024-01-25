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
    private static JwtAuthorization _jwtAuthorization = new JwtAuthorization();

    protected override void Working(Adapter adapter, HttpContext context)
    {
        // 해당 서비스가 Authentication Filter가 필요한 서비스인지 확인 필요
        if (adapter.Cluster.config.Authorization == false)
            return;

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
        if (adapter.Authorization.LoginPath == context.Request.Path &&
                context.Response.StatusCode == 200)
        {
            switch (adapter.Authorization.Type)
            {
                case "JWT":
                    _jwtAuthorization.CreateToken(adapter, context);
                    break;
                default:
                    break;
            }
        }
    }
}