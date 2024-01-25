using System.Security.Cryptography.X509Certificates;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;

namespace ApiGatewayCore.Filter;

internal abstract class DefaultFilter : IFilterBase
{
    protected abstract void Working(Adapter adapter, HttpContext context);
    protected abstract void Worked(Adapter adapter, HttpContext context);

    public async Task InvokeAsync(Adapter adapter, HttpContext context, RequestDelegate next)
    {
        Working(adapter, context);

        await next(adapter, context);

        Worked(adapter, context);
    }
}