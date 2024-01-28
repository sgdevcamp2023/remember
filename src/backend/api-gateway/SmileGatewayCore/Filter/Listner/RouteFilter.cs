using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter.Listner;

internal class RouteFilter
{
    // 동기화 추가 예정
    public async Task InvokeAsync(Adapter adapter, HttpContext context)
    {
        await adapter.Cluster.Run(context);
    }
}