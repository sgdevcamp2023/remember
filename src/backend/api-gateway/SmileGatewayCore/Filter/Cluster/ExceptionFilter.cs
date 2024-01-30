using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance.Upstream;

namespace SmileGatewayCore.Filter.Cluster;

internal class ExceptionFilter : IClusterFilterBase
{
    public async Task InvokeAsync(HttpContext context, ClusterDelegate next)
    {
        try
        {
            await next(context);
        }
        catch (Exception e)
        {
            System.Console.WriteLine(e.Message);
        }
    }
}