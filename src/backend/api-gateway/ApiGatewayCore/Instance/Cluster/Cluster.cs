
using System.Net.Sockets;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Instance;

/// <summary>
/// 1. 필터
/// 2. 로드 밸런서
/// 3. 업 스트림 전달
/// 4. 클러스터 동기화
/// 5. Queue로 동기화
/// </summary>
internal class Cluster : DefaultInstance
{
    public readonly ClusterConfig config;
    public List<Socket> sockets = new List<Socket>();

    public Cluster(ClusterConfig config)
    {
        this.config = config;
    }

    public override void Init()
    {
        foreach (var address in config.Addresses)
        {
            var socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            IPEndPoint endPoint = new IPEndPoint(IPAddress.Parse(address.Address), address.Port);

            socket.Connect(endPoint);
            sockets.Add(socket);
        }
    }

    public override Task Run()
    {
        throw new NotImplementedException();
    }

    public void Start(HttpContext context)
    {

    }

    protected override void OnReceive(Socket socket, ArraySegment<byte> buffer)
    {
        
    }

    protected override void OnSend(Socket socket, ArraySegment<byte> buffer)
    {
        
    }
}