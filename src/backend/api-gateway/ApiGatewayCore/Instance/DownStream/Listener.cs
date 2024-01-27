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

    public void Run()
    {
        for (int i = 0; i < Config.ThreadCount; i++)
        {
            RegisterAccept();
        }
    }

    public async void RegisterAccept()
    {
        Socket? socket = await _listenerSocket.AcceptAsync();
        if (socket == null)
            throw new Exception();

        await Receive(socket);
    }

    protected override async void OnReceive(Socket socket, ArraySegment<byte> buffer, int recvLen)
    {
        string requestString = Encoding.UTF8.GetString(buffer.Array!, 0, recvLen);

        HttpContext context = new HttpContext(request: requestString);
        
        Adapter? adapter = MakeAdapter(context.Request.Path);

        if(adapter == null)
            throw new Exception();

        await _filterChains.FilterStartAsync(adapter, context);

        await Send(socket, System.Text.Encoding.UTF8.GetBytes(context.Response.ToString()));
    }

    protected override void OnSend(Socket socket, int size)
    {
        System.Console.WriteLine($"Send {size} bytes");

        Disconnect(socket);
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