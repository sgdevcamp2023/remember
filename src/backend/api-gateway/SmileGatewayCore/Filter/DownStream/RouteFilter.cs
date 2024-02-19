using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter.Listner;

// Request 요청에 해당하는 Cluster를 실행하는 필터
internal class RouteFilter
{
    // 동기화 추가 예정
    public async Task InvokeAsync(Adapter adapter, HttpContext context)
    {
        await adapter.Cluster.Run(context);
    }
}