using SmileGatewayCore.Config;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance.Upstream;

namespace SmileGatewayCore.Filter.Cluster;

internal class ClusterExceptionFilter : IClusterFilterBase
{
    public async Task InvokeAsync(HttpContext context, ClusterDelegate next)
    {
        try
        {
            await next(context);
        }
        catch (Exception)
        {
            // Cluster 처리 중 에러가 발생하면 어떤 처리를?

            throw;
        }
    }
}