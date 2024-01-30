using SmileGatewayCore.Http.Context;

namespace SmileGatewayCore.Instance.Upstream;

public delegate Task ClusterDelegate(HttpContext context);

internal interface IClusterFilterChains
{
    public void UseFilter(string filterName);
    public void UseFilter<T>();
    public void UseFilter(Type type);
    public void Use(Func<ClusterDelegate, ClusterDelegate> filter);
    public Task FilterStartAsync(EndPoint endPoint, HttpContext context);
}