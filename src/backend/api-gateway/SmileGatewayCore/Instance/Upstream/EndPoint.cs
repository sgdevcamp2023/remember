using System.Net;
using System.Net.Sockets;
using System.Text;
using SmileGatewayCore.Config;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance.Upstream;

namespace SmileGatewayCore.Instance;

public class EndPoint : NetworkInstance
{
    private ConnectionPool _connectionPool;
    private AsyncLocal<HttpContext> _context = new AsyncLocal<HttpContext>();
    private IPEndPoint _ipEndpoint = null!;
    private long _usingCount = 0;
    public EndPoint(AddressConfig config)
    {
        _ipEndpoint = new IPEndPoint(IPAddress.Parse(config.Address), config.Port);
        _connectionPool = new ConnectionPool(10, _ipEndpoint);
    }
    private void IncreaseUsingCount()
    {
        Interlocked.Increment(ref _usingCount);
    }

    private void DecreaseUsingCount()
    {
        Interlocked.Decrement(ref _usingCount);
    }

    public long GetUsingCount()
    {
        return Interlocked.Read(ref _usingCount);
    }

    public async Task StartAsync(HttpContext context)
    {
        IncreaseUsingCount();

        // 초기화
        // Connection Pool 처리를 어떻게 해야될 까?
        Socket socket = await _connectionPool.RentSocket();

        // 임시
        _context.Value = context;

        // 실행
        // 만약 소켓이 종료되어 있을 경우 종료됨.
        // 연결이 끊겨있는 경우라면?
        await Send(socket, context.Request.GetStringToBytes());
        
        _connectionPool.ReturnSocket(socket);

        DecreaseUsingCount();
    }

    public override void Init()
    {

    }

    protected override async Task OnSend(Socket socket, int size)
    {
        System.Console.WriteLine($"Cluster Send {size} bytes");

        await Receive(socket);
    }

    protected override async Task OnReceive(Socket socket, ArraySegment<byte> buffer, int recvLen)
    {
        // 에러 처리 할 것
        System.Console.WriteLine($"Cluster Receive {recvLen} bytes");
        if (_context.Value == null)
            throw new System.Exception();

        if (_context.Value.Response.StatusCode == 0)
        {
            if (!_context.Value.Response.Parse(Encoding.UTF8.GetString(buffer.Array!, buffer.Offset, recvLen)))
            {
                await Receive(socket, new ArraySegment<byte>(buffer.Array!, recvLen, buffer.Count - recvLen));
            }
        }
        else
        {
            if (_context.Value.Response.IsChucked)
            {
                if (!_context.Value.Response.AppendChuckedBody(Encoding.UTF8.GetString(buffer.Array!, buffer.Offset, recvLen)))
                {
                    await Receive(socket, new ArraySegment<byte>(buffer.Array!, recvLen, buffer.Count - recvLen));
                }
            }
        }
    }
}