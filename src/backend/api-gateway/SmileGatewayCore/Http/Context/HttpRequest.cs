using System.Text;
using SmileGatewayCore.Http.Feature;
using SmileGatewayCore.Http.Header;

namespace SmileGatewayCore.Http.Context;
public class HttpRequest
{
    private IRequestFeature _requestFeature;
    private IRequestCookie? _requestCookie;
    public bool IsMultipart { get; set; } = false;
    private string _boundary = null!;
    private byte[] _endBoundary = null!;

    public HttpRequest()
    {
        _requestFeature = new RequestFeature();
    }

     public bool ByteParse(ArraySegment<byte> bytes)
    {
        string header = "";
        byte[] headerEnd = Encoding.UTF8.GetBytes("\r\n\r\n");
        int i = 0;
        for (; i <= bytes.Count - headerEnd.Length; i++)
        {
            if (bytes.Array!.Skip(bytes.Offset + i).Take(headerEnd.Length).SequenceEqual(headerEnd))
            {
                header = Encoding.UTF8.GetString(bytes.Array!, bytes.Offset, i);
                break;
            }
        }

        ParseHeader(header);
        
        if(IsMultipart)
        {
            return AppendMultipartBody(new ArraySegment<byte>(bytes.Array!, bytes.Offset + i, bytes.Count - i));
        }
        else
        {
            Body = Encoding.UTF8.GetString(bytes.Array!, bytes.Offset + i, bytes.Count - i) + "\r\n";
            System.Console.WriteLine(Body);
        }

        return true;
    }

    private void ParseHeader(string requestString)
    {
        _requestFeature = new RequestFeature();
        string[] requestLines = requestString.Split("\r\n");
        MakeRequestInfo(requestLines[0].Split(" "));

        // HttpContext 분리 생성
        for (int i = 1; i < requestLines.Count(); i++)
        {
            string[] header = requestLines[i].Split(": ");

            if (header[0] == "Cookie")
            {
                Cookie = new RequestCookie(header[1]);
                continue;
            }
            if (header[0] == "Content-Type")
            {
                if (header[1].StartsWith("multipart/form-data"))
                {
                    IsMultipart = true;
                    _boundary = "--" + requestLines[i].Split("boundary=")[1];
                    _endBoundary = Encoding.UTF8.GetBytes(_boundary + "--");
                }
            }

            if (header[0] == "Content-Length")
            {
                ContentLength = int.Parse(header[1]);
                continue;
            }

            if (header.Length > 1)
                Header.Add(header[0], header[1]);
        }

        MakeDeafultHeader();
    }
    public bool AppendMultipartBody(ArraySegment<byte> bodys)
    {
        MultipartBody.AddRange(bodys);
        bool contains = false;
        for (int i = 0; i <= bodys.Count - _endBoundary.Length; i++)
        {
            if (bodys.Array!.Skip(bodys.Offset + i).Take(_endBoundary.Length).SequenceEqual(_endBoundary))
            {
                contains = true;
                break;
            }
        }
        return contains;
    }
    private void MakeRequestInfo(string[] requestInfos)
    {
        Method = requestInfos[0];

        string[] temp = requestInfos[1].Split("?");
        Path = temp[0];

        if (temp.Length > 1)
        {
            QueryString = temp[1];
        }

        Protocol = requestInfos[2];
    }

    private string HeaderToString()
    {
        string requestString = $"{Method} {Path} {Protocol}\r\n";
        requestString += $"trace-id: {TraceId}\r\n";
        requestString += $"user-id: {UserId}\r\n";
        foreach (var header in Header)
        {
            requestString += $"{header.Key}: {header.Value}\r\n";
        }
        requestString += $"Content-Length: {ContentLength}";

        return requestString;
    }

    public byte[] GetStringToBytes()
    {
        string requestString = HeaderToString();

        byte[] buffer = System.Text.Encoding.UTF8.GetBytes(requestString);
        if (IsMultipart)
            buffer = buffer.Concat(MultipartBody.ToArray()).ToArray();
        else
            buffer = buffer.Concat(Encoding.UTF8.GetBytes(Body!)).ToArray();
        System.Console.WriteLine(requestString + Body);
        return buffer;
    }

    

    private void MakeDeafultHeader()
    {
        Header["Connection"] = "keep-alive";
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
    public string TraceId
    {
        get => _requestFeature.TraceId;
        set => _requestFeature.TraceId = value;
    }
    public string UserId
    {
        get => _requestFeature.UserId;
        set => _requestFeature.UserId = value;
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
        set => _requestCookie = value;
    }

    public List<byte> MultipartBody
    {
        get => _requestFeature.MultipartBody;
        set => _requestFeature.MultipartBody = value;
    }
}