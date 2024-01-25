using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Utils;

namespace ApiGatewayCore.Config;

public static class ErrorResponse
{
    private static ErrorCodeReader _errorCodeReader = new ErrorCodeReader();
    public static void GetErrorResponse(HttpContext context, int errorCode)
    {
        context.Response.StatusCode = 400;
        context.Response.StatusMessage = "Bad Request";

        context.Response.Body = GetErrorInfo(errorCode);
    }

    public static string GetErrorInfo(int errorCode)
    {
        if(_errorCodeReader.ErrorCodes.TryGetValue(errorCode, out string? errorInfo))
            return errorInfo;

        throw new Exception();
    }
}