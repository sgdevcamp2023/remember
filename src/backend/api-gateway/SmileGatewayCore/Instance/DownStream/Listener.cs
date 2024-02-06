using System.Net;
using System.Net.Sockets;
using System.Text;
using SmileGatewayCore.Config;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance.Upstream;
using SmileGatewayCore.Manager;
using SmileGatewayCore.Utils.Logger;

namespace SmileGatewayCore.Instance.DownStream;

// 1`. Accept
// 2. 필터
internal class Listener : NetworkInstance
{
    private Socket _listenerSocket = null!;
    private ListenerFilterChains _filterChains = new ListenerFilterChains();
    public ClusterManager _clusterManager;
    private Dictionary<string, Cluster> _clusters = new Dictionary<string, Cluster>();
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
        Socket socket = _socketPool.RentSocket();
        await _listenerSocket.AcceptAsync(socket);

        Start(socket);

        // 종료
        RegisterAccept();
    }

    private async void Start(Socket socket)
    {
        try
        {
            await Receive(socket);
        }
        catch (System.Exception e)
        {
            // Listener에서 Exception 발생시
            FileLogger.GetInstance().LogError(
                traceId: "-1",
                method: "Listener.Start",
                userId: "-1",
                message: e.Message,
                apiAddr: "127.0.0.1"
            );
        }
    }

    protected override async Task OnReceive(Socket socket, ArraySegment<byte> buffer, int recvLen)
    {
        if (recvLen == 0)
        {
            Disconnect(socket);

            throw new System.Exception();
        }

        IPEndPoint? point = socket.RemoteEndPoint as IPEndPoint;
        if (point == null)
        {
            ErrorResponse.MakeErrorResponse(new HttpResponse(), 3106);
            await Send(socket, new HttpResponse().GetStringToBytes());

            throw new System.Exception();
        }

        System.Console.WriteLine($"Listener Receive {recvLen} bytes");

        // Context 생성
        HttpContext context = new HttpContext();
        string requestString = Encoding.UTF8.GetString(buffer.Array!, 0, recvLen);
        context.Request.Parse(requestString, Config.Address.Address, Config.Address.Port);

        var endPoint = socket.RemoteEndPoint as IPEndPoint;

        // Adapter 생성
        Adapter? adapter = MakeAdapter(context.Request.Path, endPoint!.Address.ToString());

        if (adapter == null)
        {
            ErrorResponse.MakeErrorResponse(context.Response, 3106);
            await Send(socket, context.Response.GetStringToBytes());
            // Error 저장 해야됨.
            throw new System.Exception();
        }

        await _filterChains.FilterStartAsync(adapter, context);

        await Send(socket, context.Response.GetStringToBytes());
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
                return new Adapter(Config, cluster, ip);
            }
        }

        return null;
    }
}