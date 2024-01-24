using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;

namespace ApiGatewayCore.Filter.Listner;

public class RouteFilter
{
    public async Task InvokeAsync(HttpContext context)
    {
        // Cluster 작동하도록 설정
        
        await Task.CompletedTask;
    }
}