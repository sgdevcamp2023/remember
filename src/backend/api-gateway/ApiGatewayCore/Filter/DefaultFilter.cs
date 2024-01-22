using System.Security.Cryptography.X509Certificates;
using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Filter;

public abstract class DefaultFilter : IFilterBase
{
    public abstract void Working(HttpContext context);
    public abstract void Worked(HttpContext context);

    public async Task InvokeAsync(HttpContext context, RequestDelegate next)
    {
        Working(context);

        await next(context);

        Worked(context);
    }
}