using Microsoft.AspNetCore.Http;

namespace SmileGatewayCore.Http.Features;

public interface IResponseCookie
{
    public void Append(string key, string value, CookieOptions options);
    public void Append(string key, string value);
    public void Append(string header);
}