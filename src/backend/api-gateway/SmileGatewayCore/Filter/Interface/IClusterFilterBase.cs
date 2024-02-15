using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance.Upstream;

namespace SmileGatewayCore.Filter.Cluster;

public interface IClusterFilterBase
{
    public Task InvokeAsync(HttpContext context, ClusterDelegate next);
}