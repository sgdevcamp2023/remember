using SmileGatewayCore.Filter;
using SmileGatewayCore.Http.Context;

namespace SmileGatewayCore.Instance;

internal interface IFilterInstance
{
    public void UseFilter<T>();
    public void UseFilter(Type type);
    public void Use(Func<RequestDelegate, RequestDelegate> filter);
    public Task FilterStartAsync(Adapter adapter, HttpContext context);
}