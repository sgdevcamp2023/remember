using System.Collections.Concurrent;
using System.Net;
using System.Net.Sockets;

namespace SmileGatewayCore.Instance.Upstream;

// 연결 관리는 어떻게?
internal class ConnectionPool
{
    // 서버가 죽어있을 때는 어떻게 체크할 것인가?
    public int Capacity { get; private set; }
    private ConcurrentQueue<Socket> _sockets = new ConcurrentQueue<Socket>();
    private long count = 0;
    public long AliveCount { get => Interlocked.Read(ref count); }
    public void EnqueueSocket(Socket socket) { _sockets.Enqueue(socket); }

    public ConnectionPool(int capacity)
    {
        Capacity = capacity;
    }


    public async Task Init(IPEndPoint endPoint, TimeSpan timeout)
    {
        for (int i = 0; i < Capacity; i++)
        {
            // 설정 통해서 커넥션 유지
            Socket socket = CreateSocket();
            try
            {
                await ConnectAsync(socket, endPoint, timeout);
                EnqueueSocket(socket);
                AddAliveCount();
            }
            catch (System.Exception e)
            {
                System.Console.WriteLine(e.Message);
            }
        }
    }

    public async Task<Socket?> GetSocket(IPEndPoint endPoint, TimeSpan timeout)
    {
        if (_sockets.TryDequeue(out Socket? socket))
        {
            return socket;
        }
        else
        {
            if (AliveCount == Capacity)
                return null;

            Socket newSocket = CreateSocket();
            try
            {
                await ConnectAsync(newSocket, endPoint, timeout);
                AddAliveCount();
                return socket;
            }
            catch (System.Exception e)
            {
                System.Console.WriteLine(e.Message);
            }
        }

        return null;
    }

    public void AddAliveCount() => Interlocked.Increment(ref count);

    public void MinusAliveCount() => Interlocked.Decrement(ref count);

    public Socket CreateSocket()
    {
        Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        socket.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReuseAddress, true);
        socket.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.KeepAlive, true);

        return socket;
    }

    public async Task ConnectAsync(Socket socket, IPEndPoint endPoint, TimeSpan timout)
    {
        var connectTask = socket.ConnectAsync(endPoint);
        var timerTask = Task.Delay(timout);

        var completedTask = await Task.WhenAny(timerTask, connectTask);

        if (completedTask == timerTask)
            throw new TimeoutException();

        if (socket.Connected == false)
            throw new SocketException((int)SocketError.ConnectionRefused);
    }
}