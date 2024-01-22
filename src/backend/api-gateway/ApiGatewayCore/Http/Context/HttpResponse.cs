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

    public HttpResponse(string responseString)
    {
        _responseFeatrue = new ResponseFeature();
        string[] responseLines = responseString.Split("\r\n");
        string[] responseLine = responseLines[0].Split(" ");
        Method = responseLine[0];
        Path = responseLine[1];
        Protocol = responseLine[2];

        // HttpContext 분리 생성
        for(int i = 1;i<responseLines.Length;i++)
        {
            if(responseLines[i] == "\n")
            {
                Body = string.Join("\n", responseLines, i + 1, responseLines.Length - i - 1);
                break;
            }

            string[] header = responseLines[i].Split(": ");
            Header.Add(header[0],header[1]);
        }
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

    public string? Body
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