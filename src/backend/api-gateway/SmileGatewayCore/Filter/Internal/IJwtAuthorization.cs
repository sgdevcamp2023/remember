using System.Security.Claims;
using SmileGatewayCore.Config;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter.Internal;

internal interface IJwtAuthorization
{
    public JwtResponseModel CreateToken(Adapter adapter, string body);
    public void DeleteToken(Adapter adapter, HttpContext context);
    public bool ValidationToken(JwtValidator validator, string accessToken);
    public JwtResponseModel RefreshAccessToken(JwtValidator validator, JwtModel token);
    public ClaimsPrincipal? GetPrincipal(string accessToken);
}