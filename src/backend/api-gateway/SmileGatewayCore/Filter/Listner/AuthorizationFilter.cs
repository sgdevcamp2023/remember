using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using SmileGatewayCore.Filter.Internal;
using SmileGatewayCore.Exception;

namespace SmileGatewayCore.Filter.Listner;

internal class AuthorizationFilter : ListenerFilter
{
    // 생성 관리 벨리데이션 체크깔쥐 다 해주기
    private static JwtAuthorization _jwtAuthorization = new JwtAuthorization();

    protected override void Working(Adapter adapter, HttpContext context)
    {
        // 해당 서비스가 Authentication Filter가 필요한 서비스인지 확인 필요
        if (adapter.Cluster.Config.Authorization == false)
            return;

        if (adapter.Authorization == null)
            throw new ConfigException(3101);

        // 토큰이 없을 경우
        if (!context.Request.Header.TryGetValue("Authorization", out string? accessToken))
            throw new AuthException(3006);

        if (!_jwtAuthorization.ValidationToken(adapter.Authorization.jwtValidator, accessToken))
        {
            if (context.Request.Cookie == null)
                throw new AuthException(3004);

            context.Request.Cookie.TryGetValue("refreshToken", out string? refreshToken);
            if (refreshToken == null)
                throw new AuthException(3004);

            SetJwtInBody(context, _jwtAuthorization.RefreshAccessToken(adapter.Authorization.jwtValidator, new JwtModel(accessToken, refreshToken)));
        }
    }
    protected override void Worked(Adapter adapter, HttpContext context)
    {
        if (context.Response.StatusCode == 200)
        {
            if (adapter.Authorization!.LoginPath == context.Request.Path)
            {
                switch (adapter.Authorization.Type)
                {
                    case "JWT":
                        if (context.Response.Body == null)
                            throw new AuthException(3007);
                        SetJwtInBody(context, _jwtAuthorization.CreateToken(adapter, context.Response.Body));
                        break;
                    default:
                        break;
                }
            }
            else if (adapter.Authorization.LogoutPath == context.Request.Path)
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
    }

    private void SetJwtInBody(HttpContext context, JwtModel jwtModel)
    {
        string jwtJson = Newtonsoft.Json.JsonConvert.SerializeObject(jwtModel);
        context.Response.ContentLength = jwtJson.Length;
        context.Response.Body = jwtJson;
    }
}