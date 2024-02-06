using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter.Listner;

internal class ServiceFilter : ListenerFilter
{
    protected override void Working(Adapter adapter, HttpContext context)
    {
        foreach (string header in adapter.DisallowHeaders!)
        {
            // if (context.Request.Header.ContainsKey(header) == true)
                // throw new
        }
    }
    protected override void Worked(Adapter adapter, HttpContext context)
    {
        throw new NotImplementedException();
    }

}