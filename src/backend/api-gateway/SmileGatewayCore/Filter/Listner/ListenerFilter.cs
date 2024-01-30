using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using SmileGatewayCore.Instance.DownStream;

namespace SmileGatewayCore.Filter.Listner;

internal abstract class ListenerFilter : IListenerFilterBase
{
    protected abstract void Working(Adapter adapter, HttpContext context);
    protected abstract void Worked(Adapter adapter, HttpContext context);

    public async Task InvokeAsync(Adapter adapter, HttpContext context, ListenerDelegate next)
    {
        Working(adapter, context);

        await next(adapter, context);

        Worked(adapter, context);
    }
}