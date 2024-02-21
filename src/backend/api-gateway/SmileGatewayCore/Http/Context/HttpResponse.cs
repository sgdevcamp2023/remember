using SmileGatewayCore.Http.Feature;
using SmileGatewayCore.Http.Features;

namespace SmileGatewayCore.Http.Context;


public partial class HttpResponse
{
    public HttpResponse()
    {
        _responseFeatrue = new ResponseFeature();
    }

    public bool Parse(string responseString)
    {
        // System.Console.WriteLine(responseString);
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
                continue;
            if (header[0] == "Set-Cookie")
            {
                if (Cookie == null)
                    Cookie = new ResponseCookie();

                Cookie.Append(header[1]);
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
                Header[header[0]] = header[1];
        }

        return isSuccess;
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
        if (Cookie != null)
        {
            responseString += $"{Cookie.ToString()}\r\n";
        }

        if (ContentLength != -1)
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

    public void MakeAccessControlAllowOrigin()
    {
        Header["Access-Control-Allow-Credentials"] = "true";
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
                Body += bodys[i + 1];
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