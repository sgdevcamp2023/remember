using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;

namespace ApiGatewayCore.Filter.Listner;

internal class RouteFilter
{
    public async Task InvokeAsync(Adapter adapter, HttpContext context)
    {
        await adapter.Cluster.Run(context);
    }
}