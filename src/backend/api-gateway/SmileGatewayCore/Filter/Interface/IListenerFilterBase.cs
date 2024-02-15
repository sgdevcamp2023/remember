using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using SmileGatewayCore.Instance.DownStream;

namespace SmileGatewayCore.Filter.Listner;

public interface IListenerFilterBase
{
    public Task InvokeAsync(Adapter adapter, HttpContext context, ListenerDelegate next);
}