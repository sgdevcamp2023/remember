using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;

namespace ApiGatewayCore.Filter;

public class ExceptionFilter : IFilterBase
{
    public async Task InvokeAsync(Adapter adapter, HttpContext context, RequestDelegate next)
    {
        try
        {
            await next(adapter, context);
        }
        catch(Exception e)
        {
            System.Console.WriteLine(e.Message);
        }
    }
}