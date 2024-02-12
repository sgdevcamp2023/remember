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
        // 전
        _logger.LogInformation(
            traceId: context.Request.Headers["trace-id"],
            method: context.Request.Method,
            userId: context.Request.Headers["user-id"],
            message: invocation.Method.Name + " start",
            apiAddr: context.Connection.RemoteIpAddress!.ToString());

        invocation.Proceed();

        // 후
        _logger.LogInformation(
            traceId: context.Request.Headers["trace-id"],
            method: context.Request.Method,
            userId: context.Request.Headers["user-id"],
            message: invocation.Method.Name + " end",
            apiAddr: context.Connection.RemoteIpAddress!.ToString());
    }
}