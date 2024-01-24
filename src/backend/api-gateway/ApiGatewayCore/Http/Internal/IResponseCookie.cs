using Microsoft.AspNetCore.Http;

namespace ApiGatewayCore.Http.Features;

public interface IResponseCookie
{
    public void Append(string key, string value, CookieOptions options);
    public void Append(string key, string value);
}