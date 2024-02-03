using SmileGatewayCore.Http.Feature;
using SmileGatewayCore.Http.Features;
using SmileGatewayCore.Http.Header;

namespace SmileGatewayCore.Http.Context;


public class HttpResponse
{
    IResponseFeature _responseFeatrue;
    IResponseCookie _responseCookie;
    public bool IsChucked { get; set; } = false;
    public HttpResponse()
    {
        _responseFeatrue = new ResponseFeature();
        _responseCookie = new ResponseCookie(Header);
    }

    public bool Parse(string responseString)
    {
        _responseFeatrue = new ResponseFeature();
        string[] responseLines = responseString.Split("\r\n");
        MakeResponseinfo(responseLines[0].Split(" "));

        // HttpContext 분리 생성
        // Body 형태가 다름. 그래서 따로 해줘야함.
        bool isSuccess = true;
        for (int i = 1; i < responseLines.Length; i++)
        {
            if (responseLines[i] == "")
            {
                if (IsChucked)
                    isSuccess = MakeChuckedBody(responseLines.Skip(i + 1).ToArray());
                else
                    Body = string.Join("\r\n", responseLines.ToArray(), i + 1, responseLines.Length - i - 1);
                break;
            }

            string[] header = responseLines[i].Split(": ");

            if (header[0] == "Vary")
            {
                Varys.Add(responseLines[i]);
                continue;
            }
            if (header[0] == "Access-Control-Allow-Origin")
            {
                Header.Add("Access-Control-Allow-Origin", "http://localhost:3000");
                continue;
            }

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
            if (header[0] == "Transfer-Encoding" && header[1] == "chunked")
            {
                IsChucked = true;
                continue;
            }

            if (header.Length > 1)
                Header.Add(header[0], header[1]);
        }

        _responseCookie = new ResponseCookie(Header);

        return isSuccess;
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
    public string TraceId
    {
        get => _responseFeatrue.TraceId;
        set => _responseFeatrue.TraceId = value;
    }
    public string UserId
    {
        get => _responseFeatrue.UserId;
        set => _responseFeatrue.UserId = value;
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
    public List<string> Varys
    {
        get => _responseFeatrue.Varys;
        set => _responseFeatrue.Varys = value;
    }
    public override string ToString()
    {
        // Emulator 고쳐야함.
        string responseString = $"{Protocol} {StatusCode} {StatusMessage}\r\n";
        foreach (var vary in Varys)
        {
            responseString += $"{vary}\r\n";
        }
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

    public bool AppendChuckedBody(string body)
    {
        string[] str = body.Split("\r\n");

        return MakeChuckedBody(str);
    }

    private bool MakeChuckedBody(string[] bodys)
    {
        bool isEnd = false;
        for (int i = 0; i < bodys.Length; i += 2)
        {
            if (bodys[i] == "")
                break;

            int length = Convert.ToInt32(bodys[i], 16);
            ContentLength += length;

            if (length == 0)
            {
                isEnd = true;
                break;
            }
            else
                Body += bodys[i + 1] + "\r\n";
        }

        return isEnd;
    }

    private void MakeResponseinfo(string[] responseInfos)
    {
        Protocol = responseInfos[0];
        StatusCode = int.Parse(responseInfos[1]);
        StatusMessage = string.Join(" ", responseInfos.Skip(2).ToArray());
    }
}