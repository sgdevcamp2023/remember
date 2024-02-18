using SmileGatewayCore.Config;
using SmileGatewayCore.Exception;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using SmileGatewayCore.Instance.DownStream;
using SmileGatewayCore.Utils.Logger;

namespace SmileGatewayCore.Filter.Listner;

public class ExceptionFilter : IListenerFilterBase
{
    public async Task InvokeAsync(Adapter adapter, HttpContext context, ListenerDelegate next)
    {
        try
        {
            await next(adapter, context);
        }
        catch (System.Exception e)
        {
            if (e is DefaultException d)
            {
                System.Console.WriteLine($"ExceptionFilter: {d.Message} {d.ErrorCode}");

                ErrorResponse.Instance.MakeBadRequest(context.Response, d.ErrorCode);
            }
            else if (e is InternalException i)
            {
                System.Console.WriteLine($"ExceptionFilter: {i.Message} {i.ErrorCode}");

                ErrorResponse.Instance.MakeInternalServerError(context.Response, i.ErrorCode);
            }
            else
            {
                System.Console.WriteLine($"ExceptionFilter: {e.Message}");
                ErrorResponse.Instance.MakeBadRequest(context.Response, 3000);
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