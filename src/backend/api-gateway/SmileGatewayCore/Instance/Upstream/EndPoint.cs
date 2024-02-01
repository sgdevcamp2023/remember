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
        // Socket? socket = _connectionPool.RentSocket();
        // if (socket == null)
        //     throw new Exception();

        // 임시
        Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        _context.Value = context;

        await socket.ConnectAsync(_ipEndpoint);

        // 실행
        // 만약 소켓이 종료되어 있을 경우 종료됨.
        await Send(socket, context.Request.GetStringToBytes());
        await Receive(socket);

        socket.Disconnect(false);

        // _connectionPool.ReturnSocket(socket);

        DecreaseUsingCount();
    }

    public override void Init()
    {

    }

    protected override void OnSend(Socket socket, int size)
    {
        System.Console.WriteLine($"Send {size} bytes");
    }

    protected override void OnReceive(Socket socket, ArraySegment<byte> buffer, int recvLen)
    {
        System.Console.WriteLine($"Receive {recvLen} bytes");
        if (_context.Value == null)
            throw new Exception();

        if(_context.Value.Response.StatusCode == 0)
        {
            if(!_context.Value.Response.Parse(Encoding.UTF8.GetString(buffer.Array!, buffer.Offset, recvLen)))
            {
                Receive(socket, new ArraySegment<byte>(buffer.Array!, recvLen, buffer.Count - recvLen));
            }
        }
        else
        {
            if(_context.Value.Response.IsChucked)
            {
                if(!_context.Value.Response.AppendChuckedBody(Encoding.UTF8.GetString(buffer.Array!, buffer.Offset, recvLen)))
                {
                    Receive(socket, new ArraySegment<byte>(buffer.Array!, recvLen, buffer.Count - recvLen));
                }
            }
        }
    }
}