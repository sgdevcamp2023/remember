using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance.Upstream;

namespace SmileGatewayCore.Filter.Cluster;

public interface IUpStreamFilterBase
{
    public Task InvokeAsync(HttpContext context, UpStreamDelegate next);
}