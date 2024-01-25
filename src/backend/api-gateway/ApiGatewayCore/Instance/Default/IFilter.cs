using ApiGatewayCore.Filter;
using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Instance;

internal interface IFilter
{
    public void UseFilter<T>();
    public void UseFilter(Type type);
    public void Use(Func<RequestDelegate, RequestDelegate> filter);
    public void FilterStart(Adapter adapter, HttpContext context);
}