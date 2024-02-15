using System.Collections.Concurrent;
using System.Net;
using System.Net.Sockets;

namespace SmileGatewayCore.Instance.Upstream;

// 연결 관리는 어떻게?
internal class ConnectionPool
{
    // 서버가 죽어있을 때는 어떻게 체크할 것인가?
    private ConcurrentQueue<Socket> _aliveSocket = new ConcurrentQueue<Socket>();
    private ConcurrentQueue<Socket> _deadSocket = new ConcurrentQueue<Socket>();
    public int Capacity { get; private set; }
    private IPEndPoint _endPoint;
    public int DeadCount { get => _deadSocket.Count; }
    public int AliveCount { get => _aliveSocket.Count; }
    public Socket? GetAliveSocket() { return _aliveSocket.TryDequeue(out Socket? socket) ? socket : null; }
    public Socket? GetDeadSocket() { return _deadSocket.TryDequeue(out Socket? socket) ? socket : null; }
    public void EnqueueAliveSocket(Socket socket) { _aliveSocket.Enqueue(socket); }
    public void EnqueueDeadSocket(Socket socket) { _deadSocket.Enqueue(socket); }

    public ConnectionPool(int capacity, IPEndPoint endPoint)
    {
        Capacity = capacity;
        _endPoint = endPoint;

        Init().Wait();
    }


    private async Task Init()
    {
        for (int i = 0; i < Capacity; i++)
        {
            // 설정 통해서 커넥션 유지
            Socket socket = CreateSocket();
            try
            {
                await ConnectAsync(socket);
                EnqueueAliveSocket(socket);
            }
            catch (System.Exception)
            {
                EnqueueDeadSocket(socket);
            }
        }
    }

    public Socket CreateSocket()
    {
        Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        socket.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReuseAddress, true);
        socket.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.KeepAlive, true);
        
        // KeepAlive 시간 설정
        int keepAliveTime = 60 * 1000; // 60초
        socket.SetSocketOption(SocketOptionLevel.Tcp, SocketOptionName.TcpKeepAliveTime, keepAliveTime);

        // KeepAlive 간격 설정
        int keepAliveInterval = 1000; // 1초
        socket.SetSocketOption(SocketOptionLevel.Tcp, SocketOptionName.TcpKeepAliveInterval, keepAliveInterval);

        // KeepAlive 재시도 횟수 설정
        int keepAliveRetryCount = 3;
        socket.SetSocketOption(SocketOptionLevel.Tcp, SocketOptionName.TcpKeepAliveRetryCount, keepAliveRetryCount);

        // 딜레이 제거
        // socket.SetSocketOption(SocketOptionLevel.Tcp, SocketOptionName.NoDelay, true);    

        return socket;
    }

    public async Task ConnectAsync(Socket socket, int timeOut = 100)
    {
        var connectTask = socket.ConnectAsync(_endPoint);
        var timerTask = Task.Delay(timeOut);

        var completedTask = await Task.WhenAny(timerTask, connectTask);

        if (completedTask == timerTask)
            throw new TimeoutException();
        
        if(socket.Connected == false)
            throw new SocketException((int)SocketError.ConnectionRefused);
    }
}