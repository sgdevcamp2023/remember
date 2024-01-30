using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;
using SmileGatewayCore.Instance.DownStream;

namespace SmileGatewayCore.Filter.Listner;

public class ExceptionFilter : IListenerFilterBase
{
    public async Task InvokeAsync(Adapter adapter, HttpContext context, ListenerDelegate next)
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