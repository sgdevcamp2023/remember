using SmileGatewayCore.Config;
using SmileGatewayCore.Exception;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using SmileGatewayCore.Instance.DownStream;
using SmileGatewayCore.Utils.Logger;

namespace SmileGatewayCore.Filter.Listner;

public class ListenerExceptionFilter : IListenerFilterBase
{
    public async Task InvokeAsync(Adapter adapter, HttpContext context, ListenerDelegate next)
    {
        try
        {
            await next(adapter, context);
        }
        catch (System.Exception e)
        {
            System.Console.WriteLine("ListenerExceptionFilter: " + e.Message);

            if (e is DefaultException exception)
            {
                if(exception.ErrorCode == 3109)
                    ErrorResponse.MakeInternalServerError(context.Response, exception.ErrorCode);
                else
                    ErrorResponse.MakeBadRequest(context.Response, exception.ErrorCode);
            }
            else
            {
                ErrorResponse.MakeBadRequest(context.Response, 3000);
            }

            FileLogger.Instance.LogError(
                traceId: context.Request.TraceId,
                method: context.Request.Method,
                userId: context.Request.UserId,
                message: e.Message,
                apiAddr: adapter.UserIP
            );
        }
    }
}