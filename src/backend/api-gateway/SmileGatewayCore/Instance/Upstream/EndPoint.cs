using System.Net;
using System.Net.Sockets;
using System.Text;
using Microsoft.AspNetCore.HttpOverrides;
using SmileGatewayCore.Config;
using SmileGatewayCore.Exception;
using SmileGatewayCore.Http.Context;

// using Timer = System.Timers.Timer;

namespace SmileGatewayCore.Instance.Upstream;

public partial class EndPoint : NetworkInstance
{
    public IPEndPoint IpEndPoint { get; private set; } = null!;
    private ConnectionPool _connectPool;
    private AsyncLocal<HttpContext> _context = new AsyncLocal<HttpContext>();
    private long _usingCount = 0;
    private TimeSpan _connectTimeout;

    public EndPoint(AddressConfig addressConfig, int? connectTimeout, int? requestTimeout)
        : base(requestTimeout)
    {
        // ConnectionPool이 꽉찼을 때가 문제임.
        // 많은 양의 데이터가 올 때 말하는것.
        IpEndPoint = new IPEndPoint(IPAddress.Parse(addressConfig.Address), addressConfig.Port);
        _connectPool = new ConnectionPool(50);

        if (connectTimeout == null)
            _connectTimeout = TimeSpan.FromMilliseconds(Utils.Timeout.defaultTimeout);
        else
            _connectTimeout = TimeSpan.FromMilliseconds(connectTimeout.Value);

        Init();
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

        // 설정
        _context.Value = context;
        // Task timeout = Task.Delay(_connectTimeout);

        // // 서버가 죽어있다는 판단을 어떻게 할 것인가?
        // while (true)
        // {
        //     if (timeout.IsCompleted)
        //         throw new NetworkException(3200);

        //     Socket? socket = _connectPool.GetAliveSocket();
        //     if (socket == null)
        //     {
        //         if (IsHealthCheck == false)
        //         {
        //             IsHealthCheck = true;
        //             _timer.Enabled = true;
        //         }
        //         continue;
        //     }

        //     try
        //     {
        //         // 실행
        //         // 만약 소켓이 종료되어 있을 경우 종료됨.
        //         // 연결이 끊겨있는 경우라면?
        //         await Send(socket, context.Request.GetStringToBytes());
        //         _connectPool.EnqueueAliveSocket(socket);

        //         if (_connectPool.DeadCount > 0)
        //         {
        //             _timer.Enabled = true;
        //         }
        //     }
        //     catch (System.Exception)
        //     {
        //         System.Console.WriteLine("Dead Socket");

        //         if (socket.Poll(100, SelectMode.SelectRead))
        //             await _connectPool.ConnectAsync(socket, IpEndPoint, _connectTimeout);

        //         _connectPool.EnqueueDeadSocket(socket);
        //         continue;
        //     }
        //     break;
        // }

        Socket socket = new Socket(IpEndPoint.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
        try
        {
            await _connectPool.ConnectAsync(socket, IpEndPoint, _connectTimeout);
            await Send(socket, context.Request.GetStringToBytes());
            socket.Close();
        }
        catch (System.Exception)
        {
            throw new NetworkException(3200);
        }

        DecreaseUsingCount();   
    }

    protected override async Task OnSend(Socket socket, int size)
    {
        System.Console.WriteLine($"Cluster Send({socket.Handle}) {size} bytes");

        await Receive(socket);
    }

    protected override async Task OnReceive(Socket socket, ArraySegment<byte> buffer, int recvLen)
    {
        // 에러 처리 할 것
        System.Console.WriteLine($"Cluster Receive({socket.Handle}) {recvLen} bytes");

        if (_context.Value == null)
            throw new System.Exception();

        HttpResponse response = _context.Value.Response;

        if (response.StatusCode == 0)
        {
            if (!response.Parse(Encoding.UTF8.GetString(buffer.Array!, buffer.Offset, recvLen)))
            {
                await Receive(socket, new ArraySegment<byte>(buffer.Array!, buffer.Offset + recvLen, buffer.Count - recvLen));
            }
        }
        else
        {
            if (response.IsChucked)
            {
                if (!response.AppendChuckedBody(Encoding.UTF8.GetString(buffer.Array!, buffer.Offset, recvLen)))
                {
                    await Receive(socket, new ArraySegment<byte>(buffer.Array!, buffer.Offset + recvLen, buffer.Count - recvLen));
                }
            }
        }
    }
}