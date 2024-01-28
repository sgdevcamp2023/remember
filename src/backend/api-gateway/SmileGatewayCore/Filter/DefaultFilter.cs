using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter;

internal abstract class DefaultFilter : IFilterBase
{
    protected abstract void Working(Adapter adapter, HttpContext context);
    protected abstract void Worked(Adapter adapter, HttpContext context);

    public async Task InvokeAsync(Adapter adapter, HttpContext context, RequestDelegate next)
    {
        Working(adapter, context);

        await next(adapter, context);

        Worked(adapter, context);
    }
}