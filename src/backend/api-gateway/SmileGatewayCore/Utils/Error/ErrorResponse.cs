using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Utils;

namespace SmileGatewayCore.Config;

public class ErrorResponse
{
    private static ErrorResponse? _instance;
    public static ErrorResponse Instance
    {
        get
        {
            if (_instance == null)
                _instance = new ErrorResponse();
            return _instance;
        }
    }

    private ErrorCodeReader _errorCodeReader = new ErrorCodeReader();
    public void MakeBadRequest(HttpResponse response, int errorCode)
    {
        response.StatusCode = 400;
        response.StatusMessage = "Bad Request";
        response.Protocol = "HTTP/1.1";
        response.Header["Content-Type"] = "applicatoin/json";
        response.Header["Date"] = DateTime.Now.ToString("r");
        response.Header["Connection"] = "close";
        response.MakeAccessControlAllowOrigin();

        string errorString = GetErrorInfo(errorCode);
        response.ContentLength = errorString.Length;
        response.Body = errorString;
    }

    public void MakeInternalServerError(HttpResponse response, int errorCode)
    {
        response.StatusCode = 500;
        response.StatusMessage = "Internal Server Error";
        response.Protocol = "HTTP/1.1";
        response.Header["Content-Type"] = "applicatoin/json";
        response.Header["Date"] = DateTime.Now.ToString("r");
        response.Header["Connection"] = "close";
        response.MakeAccessControlAllowOrigin();

        string errorString = GetErrorInfo(errorCode);
        response.ContentLength = errorString.Length;
        response.Body = errorString;
    }
    public string GetErrorInfo(int errorCode)
    {
        if (_errorCodeReader.ErrorCodes.TryGetValue(errorCode, out string? errorInfo))
            return errorInfo;

        return "";
    }
}