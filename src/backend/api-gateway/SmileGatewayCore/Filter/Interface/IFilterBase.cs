using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter;

public interface IFilterBase
{
    public Task InvokeAsync(Adapter adapter, HttpContext context, RequestDelegate next);
}