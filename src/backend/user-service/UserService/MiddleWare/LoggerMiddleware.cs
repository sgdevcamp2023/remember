using user_service.logger;

namespace user_service
{
    namespace middleware
    {
        public class LoggerMiddleware
        {
            private RequestDelegate _next;
            private IBaseLogger _logger;
            public LoggerMiddleware(RequestDelegate next, IBaseLogger logger)
            {
                _next = next;
                _logger = logger;
            }

            public async Task InvokeAsync(HttpContext context)
            {
                string traceId = context.Request.Headers["trace-id"];
                string userId = context.Request.Headers["user-id"];

                _logger.LogInformation(
                    traceId: traceId,
                    method: context.Request.Method,
                    userId: userId,
                    message: context.Request.Path + " Start",
                    apiAddr: context.Connection.RemoteIpAddress!.ToString());
                
                await _next(context);

                _logger.LogInformation(
                    traceId: traceId,
                    method: context.Request.Method,
                    userId: userId,
                    message: context.Request.Path + " End",
                    apiAddr: context.Connection.RemoteIpAddress!.ToString());
            }
        }
    }
}