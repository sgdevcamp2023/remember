using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance.Upstream;

namespace SmileGatewayCore.Filter.Cluster;

public abstract class UpStreamFilter : IUpStreamFilterBase
{
    public abstract void Working(HttpContext context);
    public abstract void Worked(HttpContext context);

    public async Task InvokeAsync(HttpContext context, UpStreamDelegate next)
    {
        Working(context);
        
        await next(context);

        Worked(context);
    }
}