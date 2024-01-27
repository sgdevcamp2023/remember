using ApiGatewayCore.Http.Feature;
using ApiGatewayCore.Http.Features;
using ApiGatewayCore.Http.Header;
using StackExchange.Redis;

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

        try
        {
            string[] responseInfo = responseLines[0].Split(" ");
            Protocol = responseInfo[0];
            StatusCode = int.Parse(responseInfo[1]);
            StatusMessage = responseInfo[2];
        }
        catch (System.Exception)
        {
            System.Console.WriteLine(responseString);
            throw new System.Exception();
        }


        // HttpContext 분리 생성
        // Body 형태가 다름. 그래서 따로 해줘야함.
        for (int i = 1; i < responseLines.Count(); i++)
        {
            if (responseLines[i] == "")
            {
                Body = string.Join("\r\n", responseLines, i + 1, responseLines.Length - i - 1);
                break;
            }

            string[] header = responseLines[i].Split(": ");

            if (header[0] == "Set-Cookie")
            {
                Header.SetCookie = header[1];
                continue;
            }
            if (header[0] == "Content-Length")
            {
                _responseFeatrue.ContentLength = int.Parse(header[1]);
                continue;
            }
            if (header.Length > 1)
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

    public int ContentLength
    {
        get => _responseFeatrue.ContentLength;
        set => _responseFeatrue.ContentLength = value;
    }

    public IResponseCookie Cookie
    {
        get => _responseCookie!;
    }
    public override string ToString()
    {
        // Emulator 고쳐야함.
        string responseString = $"{Protocol} {StatusCode} {StatusMessage}\r\n";
        foreach (var header in Header)
        {
            responseString += $"{header.Key}: {header.Value}\r\n";
        }
        responseString += $"Content-Length: {ContentLength}\r\n";
        responseString += $"\r\n{Body}";
        return responseString;
    }

    public byte[] GetStringToBytes()
    {
        string responseString = ToString();
        System.Console.WriteLine(responseString);
        return System.Text.Encoding.UTF8.GetBytes(responseString);
    }
}