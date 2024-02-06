using SmileGatewayCore.Exception;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter.Listner;

internal class OriginalFilter : ListenerFilter
{
    protected override void Working(Adapter adapter, HttpContext context)
    {
        context.Request.Header["Origin"] = $"http://{adapter.Address.Address}:3000";
        if(context.Request.Header.ContainsKey("Host"))
            context.Request.Header["Host"] = $"{adapter.Address.Address}:{adapter.Address.Port}";
    }

    protected override void Worked(Adapter adapter, HttpContext context)
    {
        if(context.Response.Header.ContainsKey("Access-Control-Allow-Origin"))
            context.Response.Header["Access-Control-Allow-Origin"] = context.Request.Header["Origin"];
    }
}