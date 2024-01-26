using ApiGatewayCore.Http.Feature;
using ApiGatewayCore.Http.Features;
using ApiGatewayCore.Http.Header;

namespace ApiGatewayCore.Http.Context;


public class HttpResponse
{
    IResponseFeature _responseFeatrue;
    IResponseCookie _responseCookie;

    public HttpResponse()
    {
        _responseFeatrue = new ResponseFeature();
        _responseCookie = new ResponseCookie(Header);
    }

    public HttpResponse(string responseString)
    {
        _responseFeatrue = new ResponseFeature();
        string[] responseLines = responseString.Split("\r\n");
        string[] responseLine = responseLines[0].Split(" ");
        Protocol = responseLine[0];
        StatusCode = int.Parse(responseLine[1]);
        StatusMessage = responseLine[2];

        // HttpContext 분리 생성
        for (int i = 1; i < responseLines.Length; i++)
        {
            if (responseLines[i] == "\n")
            {
                Body = string.Join("\n", responseLines, i + 1, responseLines.Length - i - 1);
                break;
            }

            if (responseLine[i] == "Set-Cookie")
            {
                Header.SetCookie = responseLines[i];
                continue;
            }
            if (responseLine[i] == "Content-Length")
            {
                _responseFeatrue.ContentLength = int.Parse(responseLines[i]);
                continue;
            }

            string[] header = responseLines[i].Split(": ");
            Header.Add(header[0], header[1]);
        }

        _responseCookie = new ResponseCookie(Header);
    }
    public string Protocol
    {
        get => _responseFeatrue.Protocol;
        set => _responseFeatrue.Protocol = value;
    }

    public int StatusCode
    {
        get => _responseFeatrue.StatusCode;
        set => _responseFeatrue.StatusCode = value;
    }

    public string StatusMessage
    {
        get => _responseFeatrue.StatusMessage;
        set => _responseFeatrue.StatusMessage = value;
    }

    public HeaderDictionary Header
    {
        get => _responseFeatrue.Header;
        set => _responseFeatrue.Header = value;
    }

    public string? Body
    {
        get => _responseFeatrue.Body;
        set => _responseFeatrue.Body = value;
    }

    public IResponseCookie Cookie
    {
        get => _responseCookie!;
    }
    public override string ToString()
    {
        string responseString = $"{Protocol} {StatusCode} {StatusMessage}\r\n";
        foreach (var header in Header)
        {
            responseString += $"{header.Key}: {header.Value}\r\n";
        }
        responseString += $"\n{Body}";
        return responseString;
    }
}