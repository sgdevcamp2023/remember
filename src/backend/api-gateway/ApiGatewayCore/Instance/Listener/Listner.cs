using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Instance.Listener;

public class Listener : AbstractFilter
{
    private HttpContext _context;
    public Listener()
    {
        
    }
}