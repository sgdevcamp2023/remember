using ApiGatewayCore.Http.Feature;
using ApiGatewayCore.Http.Header;

namespace ApiGatewayCore.Http.Context;
public class HttpRequest
{
    IRequestFeature _requestFeature;

    public HttpRequest()
    {
        _requestFeature = new RequestFeature();
    }

    public string Method
    {
        get => _requestFeature.Method;
        set => _requestFeature.Method = value;
    }

    public string Path
    {
        get => _requestFeature.Path;
        set => _requestFeature.Path = value;
    }

    public string Protocol
    {
        get => _requestFeature.Protocol;
        set => _requestFeature.Protocol = value;
    }

    public HeaderDictionary Header
    {
        get => _requestFeature.Header;
        set => _requestFeature.Header = value;
    }

    public string Body
    {
        get => _requestFeature.Body;
        set => _requestFeature.Body = value;
    }

    public string ToRequestString()
    {
        string requestString = $"{Method} {Path} {Protocol}\r\n";
        foreach (var header in Header)
        {
            requestString += $"{header.Key}: {header.Value}\r\n";
        }
        requestString += $"\n{Body}";
        return requestString;
    }
}