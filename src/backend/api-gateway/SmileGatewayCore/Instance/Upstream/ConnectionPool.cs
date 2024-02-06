using System.Collections.Concurrent;
using System.Net;
using System.Net.Sockets;

namespace SmileGatewayCore.Instance.Upstream;

// 연결 관리는 어떻게?
internal class ConnectionPool
{
    // 서버가 죽어있을 때는 어떻게 체크할 것인가?
    private ConcurrentQueue<Socket> _connections = new ConcurrentQueue<Socket>();
    private int _capacity;
    private IPEndPoint _endPoint;

    public ConnectionPool(int capacity, IPEndPoint endPoint)
    {
        // _capacity = capacity;
        _capacity = capacity;
        _endPoint = endPoint;

        Init();
    }

    private void Init()
    {
        for (int i = 0; i < _capacity; i++)
        {
            // 설정 통해서 커넥션 유지
            try
            {
                Socket socket = CreateSocket();
                socket.Connect(_endPoint);
                _connections.Enqueue(socket);
            }
            catch (SocketException e)
            {
                System.Console.WriteLine(e.Message);
            }
        }
    }
    public Socket RentSocket()
    {
        CancellationTokenSource cts = new CancellationTokenSource(TimeSpan.FromSeconds(1)); // 타임 제한
        // 타임 제한
        while (true)
        {
            if (_connections.TryDequeue(out Socket? socket))
            {
                return socket;
            }

        }
    }

    public void ReturnSocket(Socket socket)
    {
        _connections.Enqueue(socket);
    }

    private Socket CreateSocket()
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
        socket.SetSocketOption(SocketOptionLevel.Tcp, SocketOptionName.NoDelay, true);

        return socket;
    }
}