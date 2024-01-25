using System.Security.Claims;
using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;

namespace ApiGatewayCore.Filter;

internal interface IJwtAuthorization
{
    public void CreateToken(Adapter adapter, HttpContext context);
    public bool ValidationToken(string accessToken);
    public string RefreshAccessToken(string refreshToken);
    public ClaimsPrincipal? GetPrincipal(string accessToken, string secretKey);
}