using SmileGatewayCore.Exception;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter.Listner;

internal class OriginalFilter : ListenerFilter
{
    protected override void Working(Adapter adapter, HttpContext context)
    {
        context.Response.Header["Access-Control-Allow-Origin"] = context.Request.Header["Origin"];

        context.Request.Header["Origin"] = $"http://{adapter.Address.Address}:3000";
        if (context.Request.Header.ContainsKey("Host"))
            context.Request.Header["Host"] = $"{adapter.Address.Address}:5000";
    }

    protected override void Worked(Adapter adapter, HttpContext context)
    {
        // Origin을 확인해서, https인지 http인지 확인해서 넘겨줘야 할 듯?
        // context.Response.Header["Access-Control-Allow-Origin"] = $"https://localhost:3000";
    }
}