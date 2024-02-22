using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using SmileGatewayCore.Filter.Internal;
using SmileGatewayCore.Exception;
using System.Security.Claims;

namespace SmileGatewayCore.Filter.Listner;

internal class AuthorizationFilter : DownStreamFilter
{
    // 생성 관리 벨리데이션 체크깔쥐 다 해주기
    private static JwtAuthorization _jwtAuthorization = new JwtAuthorization();

    protected override void Working(Adapter adapter, HttpContext context)
    {
        // 해당 서비스가 Authentication Filter가 필요한 서비스인지 확인 필요
        if (adapter.Cluster.Config.Authorization == false)
            return;

        if (context.Request.Method == "OPTIONS")
            return;

        if (adapter.Authorization == null)
            throw new ConfigException(3101);

        // 토큰이 없을 경우
        if (!context.Request.Header.TryGetValue("Authorization", out string? accessToken))
            throw new AuthException(3006);

        string[] tokens = accessToken.Split(" ");
        accessToken = tokens[1];

        if (tokens[0] != "Bearer")
            throw new AuthException(3003);

        if (adapter.JwtValidator == null)
            throw new ConfigException(3102);

        if (!_jwtAuthorization.ValidationToken(adapter.JwtValidator, accessToken))
        {
            System.Console.WriteLine("Authorization Filter : Token Validation Failed");
            // 실패시 Refresh Token을 확인
            if (context.Request.Cookie == null)
                throw new AuthException(3004);

            context.Request.Cookie.TryGetValue("refreshToken", out string? refreshToken);
            if (refreshToken == null)
                throw new AuthException(3004);

            // throw
            SetJwtInBody(context, _jwtAuthorization.RefreshAccessToken(adapter.JwtValidator, new JwtModel(accessToken, refreshToken)));
        }
        else
        {
            // 성공시 토큰에서 id를 추출, 헤더에 저장
            ClaimsPrincipal? claims = _jwtAuthorization.GetPrincipal(adapter.JwtValidator, accessToken);
            if (claims == null)
                throw new AuthException(3000);

            string? id = claims.Identity?.Name;
            if (id == null)
                throw new AuthException(3000);

            context.Request.UserId = id;
        }
    }
    protected override void Worked(Adapter adapter, HttpContext context)
    {
        if (adapter.Authorization == null)
            return;

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
                if (adapter.JwtValidator == null)
                    throw new ConfigException(3102);

                switch (adapter.Authorization.Type)
                {
                    case "JWT":
                        _jwtAuthorization.DeleteToken(adapter.JwtValidator, context);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void SetJwtInBody(HttpContext context, JwtResponseModel jwtModel)
    {
        string jwtJson = Newtonsoft.Json.JsonConvert.SerializeObject(jwtModel);
        context.Response.ContentLength = jwtJson.Length;
        context.Response.Body = jwtJson;
    }
}