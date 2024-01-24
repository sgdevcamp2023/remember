using System.Net;
using System.Net.Sockets;
using System.Text;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Manager;

namespace ApiGatewayCore.Instance.Listener;

public class Listener : DefaultInstance
{
    private Socket _listenerSocket = null!;
    private readonly ListenerConfig _config = null!;
    ThreadLocal<ClusterManager> _clusters = new ThreadLocal<ClusterManager>();
    public Listener(ListenerConfig model)
    {
        _config = model;
    }
    public override void Init()
    {
        // 필터 설정
        // UseFilter<ExceptionFilter>();
        // UseFilter<ServiceFilter>();
        // UseFilter<ProtocolCheckFilter>();
        // UseFilter<AuthenticationFilter();
        // UseFilter<ConnectionFilter>();

        // 소켓 설정
        _listenerSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        IPEndPoint endPoint = new IPEndPoint(IPAddress.Parse(_config.Address.Address), _config.Address.Port);

        _listenerSocket.Bind(endPoint);
        _listenerSocket.Listen(1);
    }

    public override async Task Run()
    {
        for (int i = 0; i < _config.ThreadCount; i++)
        {
            SocketAsyncEventArgs args = new SocketAsyncEventArgs();
            args.Completed += new EventHandler<SocketAsyncEventArgs>(OnAcceptCompleted);
            RegisterAccept(args);
        }

        await Task.Delay(-1);
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

            Receive(socket);
        }
        else
            throw new Exception();

        RegisterAccept(args);
    }

    protected override void OnReceive(Socket socket, ArraySegment<byte> buffer)
    {
        string requestString = Encoding.UTF8.GetString(buffer.Array!, 0, buffer.Count);

        HttpContext context = new HttpContext(request: requestString);

        // FilterStart(context);

        Send(socket, System.Text.Encoding.UTF8.GetBytes(context.Response.ToResponseString()));
    }

    protected override void OnSend(Socket socket, ArraySegment<byte> buffer)
    {
        throw new NotImplementedException();
    }
}