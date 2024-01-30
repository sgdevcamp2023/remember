using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using SmileGatewayCore.Instance.DownStream;

namespace SmileGatewayCore.Filter.Listner;

public abstract class CustomFilter : IListenerFilterBase
{
    public abstract void Working(HttpContext context);
    public abstract void Worked(HttpContext context);

    public async Task InvokeAsync(Adapter adapter, HttpContext context, ListenerDelegate next)
    {
        Working(context);
        
        await next(adapter, context);

        Worked(context);
    }
}