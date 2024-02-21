using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter.Listner;

// Request 요청에 TraceID를 부여하는 필터
internal class TraceFilter : DownStreamFilter
{
    static long TraceId = 1;
    protected override void Working(Adapter adapter, HttpContext context)
    {
        if(context.Request.TraceId != String.Empty)
            return;
        
        long traceId = Interlocked.Increment(ref TraceId);
        context.Request.TraceId = traceId.ToString();
        context.Response.TraceId = traceId.ToString();
    }
    
    protected override void Worked(Adapter adapter, HttpContext context)
    {
           
    }
}