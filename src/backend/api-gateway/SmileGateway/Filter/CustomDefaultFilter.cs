using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;

namespace ApiGatewayCore.Filter;

public abstract class CustomDefaultFilter : IFilterBase
{
    public abstract void Working(HttpContext context);
    public abstract void Worked(HttpContext context);

    public async Task InvokeAsync(Adapter adapter, HttpContext context, RequestDelegate next)
    {
        Working(context);
        
        await next(adapter, context);

        Worked(context);
    }
}