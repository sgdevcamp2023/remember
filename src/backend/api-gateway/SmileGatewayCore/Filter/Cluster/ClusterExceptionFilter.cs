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
        catch (Exception e)
        {
            System.Console.WriteLine(e.Message);
            ErrorResponse.MakeErrorResponse(context.Response, 4100);
        }
    }
}