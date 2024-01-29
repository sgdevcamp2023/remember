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
        // 전2
        _logger.LogInformation(
            service: invocation.TargetType.Name,
            traceId: "",
            method: context.Request.Method,
            userId: "",
            message: invocation.Method.Name + " start",
            apiAddr: context.Connection.RemoteIpAddress!.ToString());

        try
        {
            invocation.Proceed();

            _logger.LogInformation(
                service: invocation.TargetType.Name,
                traceId: "",
                method: context.Request.Method,
                userId: "",
                message: invocation.Method.Name + " end",
                apiAddr: context.Connection.RemoteIpAddress!.ToString());
        }
        catch (Exception)
        {
            _logger.LogWarning(service: invocation.TargetType.Name,
                traceId: "",
                method: context.Request.Method,
                userId: "",
                message: invocation.Method.Name + " Error",
                apiAddr: context.Connection.RemoteIpAddress!.ToString());

            // 현재 예외를 그대로 던짐
            throw;
        }
        // 후

    }
}