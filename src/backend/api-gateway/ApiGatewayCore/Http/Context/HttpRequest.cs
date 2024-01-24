using ApiGatewayCore.Http.Feature;
using ApiGatewayCore.Http.Header;

namespace ApiGatewayCore.Http.Context;
public class HttpRequest
{
    private IRequestFeature _requestFeature;
    private IRequestCookie? _requestCookie;
    public HttpRequest()
    {
        _requestFeature = new RequestFeature();
    }

    public HttpRequest(string requestString)
    {
        _requestFeature = new RequestFeature();
        string[] requestLines = requestString.Split("\r\n");
        string[] requestLine = requestLines[0].Split(" ");
        Method = requestLine[0];
        Path = requestLine[1].Split("?")[0];
        QueryString = requestLine[1].Split("?")[1];
        Protocol = requestLine[2];

        // HttpContext 분리 생성
        for(int i = 1;i<requestLines.Length;i++)
        {
            if(requestLines[i] == "\n")
            {
                Body = string.Join("\n", requestLines, i + 1, requestLines.Length - i - 1);
                break;
            }
            if(requestLine[i] == "Cookie")
            {
                _requestCookie = new RequestCookie(requestLines[i]);
                continue;
            }

            string[] header = requestLines[i].Split(": ");
            Header.Add(header[0],header[1]);
        }
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

    public string? Body
    {
        get => _requestFeature.Body;
        set => _requestFeature.Body = value;
    }

    public string? QueryString
    {
        get => _requestFeature.QueryString;
        set => _requestFeature.QueryString = value;
    }

    public IRequestCookie? Cookie
    {
        get => _requestCookie;
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