using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using SmileGatewayCore.Instance.DownStream;
using SmileGatewayCore.Utils.Logger;

namespace SmileGatewayCore.Filter.Listner;

internal class LogFilter : ListenerFilter
{
    protected override void Working(Adapter adapter, HttpContext context)
    {
        FileLogger.GetInstance().LogInformation(
            traceId: context.Request.TraceId,
            method: context.Request.Method,
            userId: context.Request.UserId,            
            message: context.Request.Path + " Start",
            apiAddr: adapter.UserIP
        );
    }
    protected override void Worked(Adapter adapter, HttpContext context)
    {
        FileLogger.GetInstance().LogInformation(
            traceId: context.Request.TraceId,
            method: context.Response.StatusCode.ToString(),
            userId: context.Request.UserId,
            message: context.Request.Path + " End",
            apiAddr: adapter.UserIP
        );
    }

}