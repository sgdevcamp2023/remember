using ApiGatewayCore.Http.Feature;
using ApiGatewayCore.Http.Header;

namespace ApiGatewayCore.Http.Context;


public class HttpResponse
{
    IResponseFeature _responseFeatrue;

    public HttpResponse()
    {
        _responseFeatrue = new ResponseFeature();
    }

    public string Method
    {
        get => _responseFeatrue.Method;
        set => _responseFeatrue.Method = value;
    }

    public string Path
    {
        get => _responseFeatrue.Path;
        set => _responseFeatrue.Path = value;
    }

    public string Protocol
    {
        get => _responseFeatrue.Protocol;
        set => _responseFeatrue.Protocol = value;
    }

    public HeaderDictionary Header
    {
        get => _responseFeatrue.Header;
        set => _responseFeatrue.Header = value;
    }

    public string Body
    {
        get => _responseFeatrue.Body;
        set => _responseFeatrue.Body = value;
    }
    public string ToResponseString()
    {
        string responseString = $"{Method} {Path} {Protocol}\r\n";
        foreach (var header in Header)
        {
            responseString += $"{header.Key}: {header.Value}\r\n";
        }
        responseString += $"\n{Body}";
        return responseString;
    }
}