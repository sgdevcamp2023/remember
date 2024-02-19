using SmileGatewayCore.Filter.Listner;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

public class ListenerTestFilter : DownStreamFilter
{
    protected override void Working(Adapter adapter, HttpContext context)
    {
        // throw new NotImplementedException();
    }
    protected override void Worked(Adapter adapter, HttpContext context)
    {
        // throw new NotImplementedException();
    }

}