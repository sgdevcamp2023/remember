using System.Collections.Concurrent;
using System.Net;
using System.Net.Sockets;
using SmileGatewayCore.Config;
using SmileGatewayCore.Exception;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance.Upstream;
using SmileGatewayCore.Manager;
using SmileGatewayCore.Utils;

namespace SmileGatewayCore.Instance.DownStream;

/// <summary>
/// Down Stream으로부터 온 연결을 응답하는 클래스입니다.
/// </summary>

internal partial class Listener : NetworkInstance
{
    private Socket _listenerSocket = null!;
    private SocketPool _socketPool = new SocketPool();
    private ListenerFilterChains _filterChains = new ListenerFilterChains();
    private ConcurrentDictionary<string, Cluster> _clusters = new ConcurrentDictionary<string, Cluster>();
    public AsyncLocal<HttpContext?> _context = new AsyncLocal<HttpContext?>() { Value = null };
    public ClusterManager _clusterManager;
    public readonly ListenerConfig Config;

    public Listener(ClusterManager clusterManager, ListenerConfig config)
        : base(config.RequestTimeout)
    {
        _clusterManager = clusterManager;
        Config = config;
    }

    public override void Init()
    {
        // 기본 필터 설정
        foreach (string clusterName in Config.RouteConfig.Clusters)
        {
            if (_clusterManager.Clusters.TryGetValue(clusterName, out ClusterConfig? cluster))
            {
                _clusters.TryAdd(clusterName, new Cluster(cluster));
            }
            else
            {
                throw new ConfigException(3105);
            }
        }

        _filterChains.Init();

        // Listener Cumtom Filter 설정
        if (Config.CustomFilters != null)
        {
            foreach (CustomFilterConfig? filter in Config.CustomFilters)
            {
                _filterChains.UseFilter(filter.Name);
            }
        }

        _listenerSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        _listenerSocket.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReuseAddress, true);
        _listenerSocket.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReceiveBuffer, Buffers.bufferSize);

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
        // 시작
        // 리눅스에서는 미 지원
        // Socket socket = _socketPool.RentSocket();
        // await _listenerSocket.AcceptAsync(socket);
        try
        {
            Socket socket = await _listenerSocket.AcceptAsync();
            Start(socket);
        }
        catch (System.Exception e)
        {
            System.Console.WriteLine(e);
        }

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
            System.Console.WriteLine(e);
        }
        
        socket.Close();
        // _socketPool.ReturnSocket(socket);
    }

    protected override async Task OnReceive(Socket socket, ArraySegment<byte> buffer, int recvLen)
    {
        if (recvLen == 0)
        {
            await Disconnect(socket);
            return;
        }

        System.Console.WriteLine($"Listener Receive {recvLen} bytes");

        // Context 생성
        if (_context.Value == null)
        {
            _context.Value = new HttpContext();

            if (!_context.Value.Request.ByteParse(new ArraySegment<byte>(buffer.Array!, buffer.Offset, recvLen)))
            {
                // Multipart의 경우 용량이 커지는 경우가 있기에 Buffer의 크기를 추가 할당
                if (_context.Value.Request.IsMultipart)
                {
                    var multipartBytes = _memory.RentMultipartBytes();
                    buffer = new ArraySegment<byte>(buffer.Array!.Concat(multipartBytes).ToArray());
                    socket.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReceiveBuffer, Buffers.multipartSize);
                }

                await Receive(socket, new ArraySegment<byte>(buffer.Array!, buffer.Offset + recvLen, buffer.Count - recvLen));
            }

            // DownStream 시작
            var endPoint = socket.RemoteEndPoint as IPEndPoint;
            await DownstreamStart(endPoint);

            // 연결이 끊겨있는지 여부 확인
            if (socket.Connected)
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

        return Task.CompletedTask;
    }

    private Adapter? MakeAdapter(string clusterPath, string ip)
    {
        // Clister Select
        foreach (var (name, cluster) in _clusters)
        {
            if (clusterPath.StartsWith(cluster.Config.Prefix))
            {
                return new Adapter(Config.Authorization, Config.DisallowHeaders, Config.Address, cluster, ip);
            }
        }

        return null;
    }

    private async Task DownstreamStart(IPEndPoint? endPoint)
    {
        Adapter? adapter = MakeAdapter(_context.Value!.Request.Path, $"{endPoint?.Address}:{endPoint?.Port}");

        if (adapter == null)
        {
            ErrorResponse.Instance.MakeBadRequest(_context.Value.Response, 3107);
            return;
        }

        await _filterChains.FilterStartAsync(adapter, _context.Value!);
    }
}