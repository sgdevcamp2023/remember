using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Filter
{
    public interface IFilterBase
    {
        public Task InvokeAsync(HttpContext context, RequestDelegate next);
    }
}