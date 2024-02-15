using Castle.DynamicProxy;
using user_service.logger;
namespace user_service.intercepter;

public class LogInterceptor : IInterceptor
{
    private IBaseLogger _logger;
    private IHttpContextAccessor _contextAccessor;
    public LogInterceptor(IBaseLogger logger, IHttpContextAccessor contextAccessor)
    {
        _logger = logger;
        _contextAccessor = contextAccessor;
    }

    public void Intercept(IInvocation invocation)
    {
        HttpContext context = _contextAccessor.HttpContext!;
        string traceId = context.Request.Headers["trace-id"];
        string userId = context.Request.Headers["user-id"];

        // 전
        _logger.LogInformation(
            traceId: traceId,
            method: context.Request.Method,
            userId: userId,
            message: invocation.Method.Name + " start",
            apiAddr: context.Connection.RemoteIpAddress!.ToString());

        invocation.Proceed();

        // 후
        _logger.LogInformation(
            traceId: traceId,
            method: context.Request.Method,
            userId: userId,
            message: invocation.Method.Name + " end",
            apiAddr: context.Connection.RemoteIpAddress!.ToString());
    }
}