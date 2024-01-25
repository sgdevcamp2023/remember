using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;

namespace ApiGatewayCore.Filter;

public interface IFilterBase
{
    public Task InvokeAsync(Adapter adapter, HttpContext context, RequestDelegate next);
}