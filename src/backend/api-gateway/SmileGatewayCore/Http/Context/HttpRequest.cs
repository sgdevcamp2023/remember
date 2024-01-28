using SmileGatewayCore.Http.Feature;
using SmileGatewayCore.Http.Header;

namespace SmileGatewayCore.Http.Context;
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

        try
        {
            string[] requestInfo = requestLines[0].Split(" ");
            Method = requestInfo[0];

            string[] temp = requestInfo[1].Split("?");
            Path = temp[0];
            // QueryString = requestInfo[1].Split("?");

            if (temp.Length > 1)
            {
                QueryString = temp[1];
            }

            Protocol = requestInfo[2];
        }
        catch (System.Exception)
        {
            System.Console.WriteLine(requestString);
            throw new System.Exception();
        }


        // HttpContext 분리 생성
        for (int i = 1; i < requestLines.Count(); i++)
        {
            if (requestLines[i] == "")
            {
                Body = string.Join("\r\n", requestLines, i + 1, requestLines.Length - i - 1);
                break;
            }
            string[] header = requestLines[i].Split(": ");

            if (header[0] == "Host")
            {
                Header.Add("Host", "127.0.0.1:5000");
                continue;
            }
            if (header[0] == "Cookie")
            {
                _requestCookie = new RequestCookie(header[1]);
                continue;
            }

            if (header[0] == "Content-Length")
            {
                _requestFeature.ContentLength = int.Parse(header[1]);
                continue;
            }

            if (header.Length > 1)
                Header.Add(header[0], header[1]);
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

    public int ContentLength
    {
        get => _requestFeature.ContentLength;
        set => _requestFeature.ContentLength = value;
    }

    public IRequestCookie? Cookie
    {
        get => _requestCookie;
    }

    public override string ToString()
    {
        string requestString = $"{Method} {Path} {Protocol}\r\n";
        foreach (var header in Header)
        {
            requestString += $"{header.Key}: {header.Value}\r\n";
        }
        requestString += $"Content-Length: {ContentLength}\r\n";
        requestString += $"\r\n{Body}";
        return requestString;
    }

    public byte[] GetStringToBytes()
    {
        string requestString = ToString();
        System.Console.WriteLine(requestString);
        return System.Text.Encoding.UTF8.GetBytes(requestString);
    }
}