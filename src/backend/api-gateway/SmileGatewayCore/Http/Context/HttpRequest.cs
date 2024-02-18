using System.Text;
using SmileGatewayCore.Http.Feature;

namespace SmileGatewayCore.Http.Context;
public partial class HttpRequest
{
    public HttpRequest()
    {
        _requestFeature = new RequestFeature();
    }

    public bool ByteParse(ArraySegment<byte> bytes)
    {
        // 헤더와 바디의 중간 부분 찾기
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

        // 헤더 파싱
        ParseHeader(header);

        // 바디 파싱
        if (IsMultipart)
            return AppendMultipartBody(new ArraySegment<byte>(bytes.Array!, bytes.Offset + i, bytes.Count - i));
        else
            Body = Encoding.UTF8.GetString(bytes.Array!, bytes.Offset + i, bytes.Count - i) + "\r\n";

        return true;
    }

    private void ParseHeader(string requestString)
    {
        // System.Console.WriteLine("\n" + requestString + "\n");
        string[] requestLines = requestString.Split("\r\n");
        MakeRequestInfo(requestLines[0].Split(" "));

        // HttpContext 분리 생성
        for (int i = 1; i < requestLines.Count(); i++)
        {
            string[] header = requestLines[i].Split(": ");
            // System.Console.WriteLine(header[0] + ": " + header[1]);
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

            if (header[0] == "Origin")
            {
                if (header[1].StartsWith("https"))
                IsHttps = true;
                else
                    IsHttps = false;
            }

            if (header.Length > 1)
                Header[header[0]] = header[1];
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
        if (ContentLength != -1)
            requestString += $"Content-Length: {ContentLength}";

        return requestString;
    }

    public byte[] GetStringToBytes()
    {
        string requestString = HeaderToString();

        byte[] buffer = System.Text.Encoding.UTF8.GetBytes(requestString);
        if (IsMultipart)
            buffer = buffer.Concat(MultipartBody).ToArray();
        else
            buffer = buffer.Concat(Encoding.UTF8.GetBytes(Body!)).ToArray();

        // System.Console.WriteLine(requestString);
        return buffer;
    }

    private void MakeDeafultHeader()
    {
        Header["Connection"] = "keep-alive";
    }
}