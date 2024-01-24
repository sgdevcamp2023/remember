using System.Net;
using System.Net.Sockets;
using System.Text;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Instance.Listener;

public class Listener : AbstractNetwork, IListener
{
    private Socket _listenerSocket = null!;
    public ListenerModel _model;
    public Listener(ListenerModel model)
    {
        _model = model;
    }
    public override void Init()
    {
        _listenerSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        IPEndPoint endPoint = new IPEndPoint(IPAddress.Parse(_model.Address.Address), _model.Address.Port);

        _listenerSocket.Bind(endPoint);
        _listenerSocket.Listen(1);
    }

    public async Task Run()
    {
        for (int i = 0; i < _model.ThreadCount; i++)
        {
            SocketAsyncEventArgs args = new SocketAsyncEventArgs();
            args.Completed += new EventHandler<SocketAsyncEventArgs>(OnAcceptCompleted);
            RegisterAccept(args);
        }

        await Task.CompletedTask;
    }
    private HttpContext MakeHttpContext(string request)
    {
        HttpRequest httpRequest = new HttpRequest(request);
        HttpResponse httpResponse = new HttpResponse();
        return new HttpContext(httpRequest, httpResponse);
    }

    public void RegisterAccept(SocketAsyncEventArgs args)
    {
        args.AcceptSocket = null;
        bool isPending = _listenerSocket.AcceptAsync(args);
        if (!isPending)
            OnAcceptCompleted(null, args);
    }

    public void OnAcceptCompleted(object? sender, SocketAsyncEventArgs args)
    {
        if (args.SocketError == SocketError.Success)
        {
            Socket? socket = args.AcceptSocket;
            if (socket == null)
                throw new Exception();

            RegisterReceive(socket);
        }
        else
            throw new Exception();

        RegisterAccept(args);
    }

    protected override void OnReceive(ArraySegment<byte> buffer)
    {
        string request = Encoding.UTF8.GetString(buffer.Array!, 0, buffer.Count);
        HttpContext context = MakeHttpContext(request);
        FilterStart(context);
    }

    protected override void OnSend(ArraySegment<byte> buffer)
    {
        throw new NotImplementedException();
    }
}