using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;

namespace ApiGatewayCore.Filter.Listner;

internal class RouteFilter
{
    // 동기화 추가 예정
    public async Task InvokeAsync(Adapter adapter, HttpContext context)
    {
        await adapter.Cluster.Run(context);
    }
}