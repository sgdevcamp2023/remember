using System.Net;
using System.Net.Sockets;
using SmileGatewayCore.Config;
using SmileGatewayCore.Exception;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance.Upstream;
using SmileGatewayCore.Manager;

namespace SmileGatewayCore.Instance.DownStream;

/// <summary>
/// Down Stream으로부터 온 연결을 응답하는 클래스입니다.
/// </summary>
internal class Listener : NetworkInstance
{
    private Socket _listenerSocket = null!;
    private SocketPool _socketPool = new SocketPool();
    private ListenerFilterChains _filterChains = new ListenerFilterChains();
    private Dictionary<string, Cluster> _clusters = new Dictionary<string, Cluster>();
    public ClusterManager _clusterManager;
    public AsyncLocal<HttpContext?> _context = new AsyncLocal<HttpContext?>() { Value = null };
    public readonly ListenerConfig Config;

    public Listener(ClusterManager clusterManager, ListenerConfig config)
    {
        _clusterManager = clusterManager;
        Config = config;
    }

    public override void Init()
    {
        // 기본 필터 설정
        foreach (string clusterName in Config.RouteConfig.Clusters)
        {
            if (_clusterManager.Clusters.TryGetValue(clusterName, out Cluster? cluster))
            {
                _clusters.Add(clusterName, cluster.Clone());
            }
        }

        _filterChains.Init();

        if (Config.CustomFilters != null)
        {
            foreach (CustomFilterConfig? filter in Config.CustomFilters)
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

    public void Changed(ListenerConfig config)
    {
        // 1. 새로운 클러스터 추가 및 삭제
        foreach (string cluster in Config.RouteConfig.Clusters)
        {

        }
    }

    public async void RegisterAccept()
    {
        // 시작
        // Socket socket = _socketPool.RentSocket();
        Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        await _listenerSocket.AcceptAsync(socket);

        Start(socket);

        // 종료
        RegisterAccept();
    }

    private async void Start(Socket socket)
    {
        await Receive(socket);

        // _socketPool.ReturnSocket(socket);
    }

    protected override async Task OnReceive(Socket socket, ArraySegment<byte> buffer, int recvLen)
    {
        if (recvLen == 0)
        {
            Disconnect(socket);
            return;
        }

        System.Console.WriteLine($"Listener Receive {recvLen} bytes");

        // Context 생성
        if (_context.Value == null)
        {
            _context.Value = new HttpContext();

            if (!_context.Value.Request.ByteParse(new ArraySegment<byte>(buffer.Array!, buffer.Offset, recvLen)))
                await Receive(socket, new ArraySegment<byte>(buffer.Array!, buffer.Offset + recvLen, buffer.Count - recvLen));

            try
            {
                var endPoint = socket.RemoteEndPoint as IPEndPoint;
                await RequestStart(endPoint);
            }
            catch(System.Exception e)
            {
                if(e is ListenerException listenerException)
                    ErrorResponse.MakeBadRequest(_context.Value.Response, listenerException.ErrorCode);
            }

            // 연결이 끊겨있는지 여부 확인
            if(socket.Connected)
                await Send(socket, _context.Value.Response.GetStringToBytes());
        }
        else
        {
            if (!_context.Value.Request.AppendMultipartBody(new ArraySegment<byte>(buffer.Array!, buffer.Offset, recvLen)))
                await Receive(socket, new ArraySegment<byte>(buffer.Array!, buffer.Offset + recvLen, buffer.Count - recvLen));
        }
    }

    protected override Task OnSend(Socket socket, int size)
    {
        System.Console.WriteLine($"Listener Send {size} bytes");
        _socketPool.ReturnSocket(socket);
        return Task.CompletedTask;
    }

    private Adapter? MakeAdapter(string clusterPath, string ip)
    {
        // Clister Select
        foreach (var (name, cluster) in _clusters)
        {
            if (clusterPath.StartsWith(cluster.Config.Prefix))
            {
                return new Adapter(Config.Authorization, Config.Address, cluster, ip);
            }
        }

        return null;
    }

    private async Task RequestStart(IPEndPoint? endPoint)
    {
        Adapter? adapter = MakeAdapter(_context.Value!.Request.Path, $"{endPoint?.Address}:{endPoint?.Port}");

        if (adapter == null)
            throw new ListenerException(3107);

        await _filterChains.FilterStartAsync(adapter, _context.Value!);
    }
}