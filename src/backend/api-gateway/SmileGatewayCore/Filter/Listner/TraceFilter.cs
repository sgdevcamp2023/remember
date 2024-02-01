using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter.Listner;

internal class TraceFilter : ListenerFilter
{
    static long TraceId = 0;
    protected override void Working(Adapter adapter, HttpContext context)
    {
        if(context.Request.TraceId == String.Empty)
            return;
        
        long traceId = Interlocked.Increment(ref TraceId);
        context.Request.TraceId = traceId.ToString();
    }
    
    protected override void Worked(Adapter adapter, HttpContext context)
    {

    }
}