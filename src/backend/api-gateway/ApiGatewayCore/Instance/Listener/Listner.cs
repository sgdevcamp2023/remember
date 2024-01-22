using System.Net.Sockets;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Instance.Listener;

public class Listener : AbstractFilter
{
    private HttpContext _context = new ();
    public ListenerModel _model;
    // public Socket _listnerSocket;
    public Listener(ListenerModel model)
    {   
        _model = model;
    }

    public HttpContext MakeHttpContext(string request)
    {
        _context.Request = new HttpRequest(request);
        return _context;
    }
}