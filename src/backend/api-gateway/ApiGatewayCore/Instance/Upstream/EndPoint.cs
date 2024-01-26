using System.Net;
using System.Net.Sockets;
using System.Text;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance.Upstream;

namespace ApiGatewayCore.Instance;

public class EndPoint : NetworkInstance
{
    private ConnectionPool _connectionPool = new ConnectionPool();
    private IPEndPoint _ipEndpoint = null!;
    private AsyncLocal<HttpContext> _context = new AsyncLocal<HttpContext>();
    private long _usingCount = 0;

    public EndPoint(AddressConfig config)
    {
        _ipEndpoint = new IPEndPoint(IPAddress.Parse(config.Address), config.Port);
    }
    public void IncreaseUsingCount()
    {
        Interlocked.Increment(ref _usingCount);
    }

    public void DecreaseUsingCount()
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
        // await socket.Send()
        await socket.ReceiveAsync(new ArraySegment<byte>(new byte[1024]), SocketFlags.None);
        
        DecreaseUsingCount();
        await Task.CompletedTask;
    }

    public override void Init()
    {
        
    }

    protected override void OnSend(Socket socket, int size)
    {
        Receive(socket);
    }

    protected override void OnReceive(Socket socket, ArraySegment<byte> buffer, int recvLen)
    {
        HttpResponse response = new HttpResponse(Encoding.UTF8.GetString(buffer.Array!, 0, recvLen));
        if(_context.Value == null)
            throw new Exception();
        _context.Value.Response = response;

        DecreaseUsingCount();
    }

}