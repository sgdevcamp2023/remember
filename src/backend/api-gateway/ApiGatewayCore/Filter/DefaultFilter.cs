using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Filter;

public class DefaultFilter : IFilterBase
{
    public async Task InvokeAsync(HttpContext context, RequestDelegate next)
    {
        await next(context);
    }
}