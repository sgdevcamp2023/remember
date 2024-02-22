using Microsoft.AspNetCore.Http;
using Microsoft.Net.Http.Headers;
using YamlDotNet.Core.Tokens;

namespace SmileGatewayCore.Http.Features;

public class ResponseCookie : IResponseCookie
{
    private List<string> _setCookie = new List<string>();
    public ResponseCookie()
    {

    }

    public void Append(string key, string value, CookieOptions options)
    {
        var setCookieHeaderValue = new SetCookieHeaderValue(key, value)
        {
            Domain = options.Domain,
            Path = options.Path,
            Expires = options.Expires,
            MaxAge = options.MaxAge,
            Secure = options.Secure,
            SameSite = (Microsoft.Net.Http.Headers.SameSiteMode)options.SameSite,
            HttpOnly = options.HttpOnly
        };

        string cookieValue = setCookieHeaderValue.ToString();
        _setCookie.Add(cookieValue);
    }

    public void Append(string key, string value)
    {
        var setCookieHeaderValue = new SetCookieHeaderValue(key, value)
        {
            Path = "/",
        };

        string cookieValue = setCookieHeaderValue.ToString();
        _setCookie.Add(cookieValue);
    }

    public void Append(string header)
    {
        string cookieValue = "Set-Cookie: " + header;
        _setCookie.Add(cookieValue);
    }

    public override string ToString()
    {
        string result = "";
        foreach (var cookie in _setCookie)
        {
            result += cookie + "\r\n";
        }

        return result;
    }
}