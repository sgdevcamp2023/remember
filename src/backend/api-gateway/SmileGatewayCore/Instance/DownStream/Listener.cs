using System.Diagnostics;
using System.Net;
using System.Net.Sockets;
using System.Text;
using SmileGatewayCore.Config;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance.Upstream;
using SmileGatewayCore.Manager;

namespace SmileGatewayCore.Instance.DownStream;

// 1`. Accept
// 2. 필터
internal class Listener : NetworkInstance
{
    private Socket _listenerSocket = null!;
    private ListenerFilterChains _filterChains = new ListenerFilterChains();

    public ClusterManager _clusterManager;
    private Dictionary<string, Cluster> _clusters = new Dictionary<string, Cluster>();
    public ListenerConfig Config { get; private set; }

    public AsyncLocal<Stopwatch> _stopwatch = new AsyncLocal<Stopwatch>();
    
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

        _filterChains.Init();

        if(Config.CustomFilters != null)
        {
            foreach(CustomFilter? filter in Config.CustomFilters)
            {
                _filterChains.UseFilter(filter.Name);
            }
        }
        // 사용자 필터 설정

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
        
        _stopwatch.Value = new Stopwatch();
        _stopwatch.Value.Start();

        await Receive(socket);

        RegisterAccept();
    }

    protected override async void OnReceive(Socket socket, ArraySegment<byte> buffer, int recvLen)
    {
        IPEndPoint? point = socket.RemoteEndPoint as IPEndPoint;
        if(point == null)
            throw new Exception();

        // Context 생성
        HttpContext context = new HttpContext();
        string requestString = Encoding.UTF8.GetString(buffer.Array!, 0, recvLen);
        context.Request.Parse(requestString);
        
        // Adapter 생성
        Adapter? adapter = MakeAdapter(context.Request.Path);

        if(adapter == null)
            throw new Exception();

        await _filterChains.FilterStartAsync(adapter, context);

        await Send(socket, context.Response.GetStringToBytes());
    }

    protected override void OnSend(Socket socket, int size)
    {
        _stopwatch.Value!.Stop();

        System.Console.WriteLine($"Send {size} bytes, Time is {_stopwatch.Value.ElapsedMilliseconds}ms");

        Disconnect(socket);
    }

    private Adapter? MakeAdapter(string clusterPath)
    {
        // Clister Select
        foreach(var (name, cluster) in _clusters)
        {
            if(clusterPath.StartsWith(name))
            {
                return new Adapter(Config, cluster);
            }
        }

        return null;
    }
}