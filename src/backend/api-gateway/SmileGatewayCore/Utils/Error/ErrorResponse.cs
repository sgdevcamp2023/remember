using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Utils;

namespace SmileGatewayCore.Config;

public static class ErrorResponse
{
    private static ErrorCodeReader _errorCodeReader = new ErrorCodeReader();
    public static void MakeBadRequest(HttpResponse response, int errorCode)
    {
        response.StatusCode = 400;
        response.StatusMessage = "Bad Request";
        response.Protocol = "HTTP/1.1";
        response.Header["Content-Type"] = "applicatoin/json";
        response.Header["Date"] = DateTime.Now.ToString("r");
        response.Header["Connection"] = "close";
        response.Header["Access-Control-Allow-Credentials"] = "true";

        string errorString = GetErrorInfo(errorCode);
        response.ContentLength = errorString.Length;
        response.Body = errorString;
    }

    public static void MakeInternalServerError(HttpResponse response, int errorCode)
    {
        response.StatusCode = 500;
        response.StatusMessage = "Internal Server Error";
        response.Protocol = "HTTP/1.1";
        response.Header.Add("Content-Type", "applicatoin/json");
        response.Header.Add("Date", DateTime.Now.ToString("r"));
        response.Header.Add("Connection", "close");

        string errorString = GetErrorInfo(errorCode);
        response.ContentLength = errorString.Length;
        response.Body = errorString;
    }
    public static string GetErrorInfo(int errorCode)
    {
        if(_errorCodeReader.ErrorCodes.TryGetValue(errorCode, out string? errorInfo))
            return errorInfo;

        throw new System.Exception();
    }
}