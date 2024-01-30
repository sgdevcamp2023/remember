using SmileGatewayCore.Http.Context;

namespace SmileGatewayCore.Filter.Cluster;

internal class TraceFilter : ClusterFilter
{
    static long TraceId = 0;
    public override void Worked(HttpContext context)
    {
        if(context.Request.Header.ContainsKey("trace-id"))
            return;
        
        long traceId = Interlocked.Increment(ref TraceId);
        context.Request.Header.Add("trace-id", traceId.ToString());
    }

    public override void Working(HttpContext context)
    {
        
    }
}