using SmileGatewayCore.Exception;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter.Listner;

// Request Header를 판별해서, Disallow 되어있는 헤더가 있는지 확인하는 필터
internal class ServiceFilter : DownStreamFilter
{
    protected override void Working(Adapter adapter, HttpContext context)
    {
        if(adapter.DisallowHeaders == null)
            return;
        
        foreach (string header in adapter.DisallowHeaders)
        {
            if (context.Request.Header.ContainsKey(header) == true)
                throw new FilterException(3108);
        }
    }
    protected override void Worked(Adapter adapter, HttpContext context)
    {
        
    }
}