using SmileGatewayCore.Exception;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter.Listner;

internal class OriginalFilter : DownStreamFilter
{
    protected override void Working(Adapter adapter, HttpContext context)
    {
        if (context.Request.Header.ContainsKey("Host"))
            context.Request.Header["Host"] = adapter.ListenerAddress;
    }

    protected override void Worked(Adapter adapter, HttpContext context)
    {
        // Origin을 확인해서, https인지 http인지 확인해서 넘겨줘야 할 듯?
        context.Response.Header["Access-Control-Allow-Origin"] = "*";
    }
}