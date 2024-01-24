using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Filter;

public class ExceptionFilter : IFilterBase
{
    public async Task InvokeAsync(HttpContext context, RequestDelegate next)
    {
        try
        {
            await next(context);
        }
        catch(Exception e)
        {
            System.Console.WriteLine(e.Message);
        }
    }
}