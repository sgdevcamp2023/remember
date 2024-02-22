using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using SmileGatewayCore.Instance.DownStream;

namespace SmileGatewayCore.Filter.Listner;

public interface IDownStreamFilterBase
{
    public Task InvokeAsync(Adapter adapter, HttpContext context, DownStreamDelegate next);
}