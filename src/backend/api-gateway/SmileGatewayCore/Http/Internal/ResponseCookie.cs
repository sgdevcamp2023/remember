using Microsoft.AspNetCore.Http;
using Microsoft.Net.Http.Headers;
using YamlDotNet.Core.Tokens;
using HeaderDictionary = SmileGatewayCore.Http.Header.HeaderDictionary;

namespace SmileGatewayCore.Http.Features;

public class ResponseCookie : IResponseCookie
{
    private HeaderDictionary _header;
    public ResponseCookie(HeaderDictionary header)
    {
        _header = header;
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

        var cookieValue = setCookieHeaderValue.ToString();

        // _header = StringValues.Concat(_header.SetCookie, cookieValue);
        _header.SetCookie = cookieValue;        
    }

    public void Append(string key, string value)
    {
        var setCookieHeaderValue = new SetCookieHeaderValue(key, value)
        {
            Path = "/",
        };

        var cookieValue = setCookieHeaderValue.ToString();

        _header.SetCookie = cookieValue;
    }
}