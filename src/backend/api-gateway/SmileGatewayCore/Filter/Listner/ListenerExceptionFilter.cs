using SmileGatewayCore.Config;
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
        catch(System.Exception e)
        {
            System.Console.WriteLine(e.Message);
            ErrorResponse.MakeErrorResponse(context.Response, 4100);

            FileLogger.GetInstance().LogError(
                traceId: context.Request.TraceId,
                method: context.Request.Method,
                userId: context.Request.UserId,
                message: e.Message,
                apiAddr: adapter.UserIP
            );
        }
    }
}