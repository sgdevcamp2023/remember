using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;
using ApiGatewayCore.Filter.Internal;

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

        if (adapter.Authorization == null)
            throw new Exception();

        // 토큰이 없을 경우        
        if (!context.Request.Header.TryGetValue("Authorization", out string? accessToken))
            throw new Exception();

        if (!_jwtAuthorization.ValidationToken(adapter.Authorization.jwtValidator, accessToken))
        {
            if (context.Request.Cookie == null)
                throw new Exception();

            context.Request.Cookie.TryGetValue("refreshToken", out string? refreshToken);
            if (refreshToken == null)
                throw new Exception();

            SetJwtInHeader(context, _jwtAuthorization.RefreshAccessToken(adapter.Authorization.jwtValidator, new JwtModel(accessToken, refreshToken)));
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
                    if (context.Response.Body == null)
                        throw new Exception();
                    SetJwtInHeader(context, _jwtAuthorization.CreateToken(adapter, context.Response.Body.ToString()));

                    break;
                default:
                    break;
            }
        }
        else if (adapter.Authorization.LogoutPath == context.Request.Path &&
                context.Response.StatusCode == 200)
        {
            switch (adapter.Authorization.Type)
            {
                case "JWT":
                    _jwtAuthorization.DeleteToken(adapter, context);
                    break;
                default:
                    break;
            }
        }
    }

    private void SetJwtInHeader(HttpContext context, JwtModel jwtModel)
    {
        context.Response.Header["Authorization"] = jwtModel.AccessToken;
        context.Response.Cookie.Append("refreshToken", jwtModel.RefreshToken);
    }
}