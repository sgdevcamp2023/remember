using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;

namespace ApiGatewayCore.Filter.Listner;

internal class ServiceFilter : DefaultFilter
{
    protected override void Worked(Adapter adapter, HttpContext context)
    {
        throw new NotImplementedException();
    }

    protected override void Working(Adapter adapter, HttpContext context)
    {
        throw new NotImplementedException();
    }
}