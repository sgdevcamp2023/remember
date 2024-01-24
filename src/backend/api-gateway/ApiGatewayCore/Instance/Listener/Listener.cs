using System.Net;
using System.Net.Sockets;
using System.Text;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Instance.Listener;

public class Listener : DefaultInstance
{
    private Socket _listenerSocket = null!;
    public ListenerConfig _model;
    public Listener(ListenerConfig model)
    {
        _model = model;
    }
    public override void Init()
    {
        // 필터 설정
        
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
        string requestString = Encoding.UTF8.GetString(buffer.Array!, 0, buffer.Count);

        HttpContext context = new HttpContext(request: requestString);

        // FilterStart(context);
    }

    protected override void OnSend(ArraySegment<byte> buffer)
    {
        throw new NotImplementedException();
    }
}