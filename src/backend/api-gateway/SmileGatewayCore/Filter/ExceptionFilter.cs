using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter;

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