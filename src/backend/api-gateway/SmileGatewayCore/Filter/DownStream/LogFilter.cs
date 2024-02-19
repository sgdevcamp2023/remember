using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using SmileGatewayCore.Utils.Logger;

namespace SmileGatewayCore.Filter.Listner;

// Request, Response의 로깅을 하는 필터
internal class LogFilter : DownStreamFilter
{
    protected override void Working(Adapter adapter, HttpContext context)
    {
        FileLogger.Instance.LogInformation(
            traceId: context.Request.TraceId,
            method: context.Request.Method,
            userId: context.Request.UserId,            
            message: context.Request.Path + " Start",
            apiAddr: adapter.UserIP
        );
    }
    protected override void Worked(Adapter adapter, HttpContext context)
    {
        FileLogger.Instance.LogInformation(
            traceId: context.Request.TraceId,
            method: context.Response.StatusCode.ToString(),
            userId: context.Request.UserId,
            message: context.Request.Path + " End",
            apiAddr: adapter.UserIP
        );
    }

}