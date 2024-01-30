using SmileGatewayCore.Http.Context;

namespace SmileGatewayCore.Instance.DownStream;

public delegate Task ListenerDelegate(Adapter adapter, HttpContext context);

internal interface IListenerFilterChain
{
    public void UseFilter(string filterName);
    public void UseFilter<T>();
    public void UseFilter(Type type);
    public void Use(Func<ListenerDelegate, ListenerDelegate> filter);
    public Task FilterStartAsync(Adapter adapter, HttpContext context);
}