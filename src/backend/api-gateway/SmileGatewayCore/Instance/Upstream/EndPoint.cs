using System.Net;
using System.Net.Sockets;
using System.Text;
using SmileGatewayCore.Config;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance.Upstream;

namespace SmileGatewayCore.Instance;

public class EndPoint : NetworkInstance
{
    private ConnectionPool _connectionPool = new ConnectionPool();
    private AsyncLocal<HttpContext> _context = new AsyncLocal<HttpContext>();
    private IPEndPoint _ipEndpoint = null!;
    private long _usingCount = 0;

    public EndPoint(AddressConfig config)
    {
        _ipEndpoint = new IPEndPoint(IPAddress.Parse(config.Address), config.Port);
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
        var socket = _connectionPool.RentSocket();
        await socket.ConnectAsync(_ipEndpoint);

        if(!socket.Connected)
            throw new Exception();
        
        _context.Value = context;

        await Send(socket, context.Request.GetStringToBytes());
        await Receive(socket);

        await socket.DisconnectAsync(false);

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
        if(_context.Value == null)
            throw new Exception();
        
        _context.Value.Response.Parse(Encoding.UTF8.GetString(buffer.Array!, 0, recvLen));
    }
}