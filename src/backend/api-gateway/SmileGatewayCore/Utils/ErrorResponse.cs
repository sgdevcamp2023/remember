using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Utils;

namespace SmileGatewayCore.Config;

public static class ErrorResponse
{
    private static ErrorCodeReader _errorCodeReader = new ErrorCodeReader();
    public static void MakeErrorResponse(HttpContext context, int errorCode)
    {
        context.Response.StatusCode = 400;
        context.Response.StatusMessage = "Bad Request";
        context.Response.Protocol = "HTTP/1.1";
        context.Response.Header.Add("Content-Type", "applicatoin/json");
        context.Response.Header.Add("Date", DateTime.Now.ToString("r"));
        context.Response.Header.Add("Connection", "close");

        string errorString = GetErrorInfo(errorCode);
        context.Response.ContentLength = errorString.Length;
        context.Response.Body = errorString;
    }

    public static string GetErrorInfo(int errorCode)
    {
        if(_errorCodeReader.ErrorCodes.TryGetValue(errorCode, out string? errorInfo))
            return errorInfo;

        throw new Exception();
    }
}