using System.Net;
using System.Net.Sockets;
using System.Text;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance.Upstream;
using ApiGatewayCore.Manager;

namespace ApiGatewayCore.Instance.DownStream;


// 1. Accept
// 2. 필터
internal class Listener : NetworkInstance
{
    private Socket _listenerSocket = null!;
    private ListenerFilterChains _filterChains = new ListenerFilterChains();

    public ClusterManager _clusterManager;
    Dictionary<string, Cluster> _clusters = new Dictionary<string, Cluster>();
    public ListenerConfig Config { get; private set; }

    public Listener(ClusterManager clusterManager, ListenerConfig config)
    {
        _clusterManager = clusterManager;
        Config = config;
    }
    public override void Init()
    {
        // 기본 필터 설정
        foreach(string clusterName in Config.RouteConfig.Clusters)
        {
            if(_clusterManager.Clusters.TryGetValue(clusterName, out Cluster? cluster))
            {
                _clusters.Add(clusterName, cluster.Clone());
            }
        }

        // 소켓 설정
        _listenerSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        IPEndPoint endPoint = new IPEndPoint(IPAddress.Parse(Config.Address.Address), Config.Address.Port);

        _listenerSocket.Bind(endPoint);
        _listenerSocket.Listen(backlog: 100);
    }

    public async Task Run()
    {
        for (int i = 0; i < Config.ThreadCount; i++)
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

    protected override void OnReceive(Socket socket, ArraySegment<byte> buffer, int recvLen)
    {
        string requestString = Encoding.UTF8.GetString(buffer.Array!, 0, recvLen);

        HttpContext context = new HttpContext(request: requestString);
        
        Adapter? adapter = MakeAdapter(context.Request.Path);

        if(adapter == null)
            throw new Exception();

        _filterChains.FilterStart(adapter, context);

        Send(socket, System.Text.Encoding.UTF8.GetBytes(context.Response.ToString()));
    }

    protected override void OnSend(Socket socket, int size)
    {
        throw new NotImplementedException();
    }

    private Adapter? MakeAdapter(string clusterPath)
    {
        // Clister Select
        foreach(var (name, cluster) in _clusters)
        {
            if(clusterPath.Contains(cluster.config.Prefix))
            {
                return new Adapter(Config, cluster);
            }
        }

        return null;
    }
}