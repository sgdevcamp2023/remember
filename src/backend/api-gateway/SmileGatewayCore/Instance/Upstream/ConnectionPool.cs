using System.Collections.Concurrent;
using System.Net;
using System.Net.Sockets;

namespace SmileGatewayCore.Instance.Upstream;

// 연결 관리는 어떻게?
public class ConnectionPool
{
    private ConcurrentQueue<Socket> _connections = new ConcurrentQueue<Socket>();
    private int _capacity;
    private IPEndPoint _endPoint;
    
    public ConnectionPool(int capacity, IPEndPoint endPoint)
    {
        // _capacity = capacity;
        _capacity = 1;
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
                _connections.Enqueue(CreateSocket());
            }
            catch (SocketException e)
            {
                System.Console.WriteLine(e.Message);
            }
        }
    }
    public async Task<Socket> RentSocket()
    {
        // CancellationTokenSource cts = new CancellationTokenSource();
        // cts.CancelAfter(TimeSpan.FromSeconds(1));

        // 타임 제한을 건다?
        int retryCount = 0;
        while (true)
        {
            try
            {
                if (_connections.TryDequeue(out Socket? socket))
                {
                    if (++retryCount > 5)
                        throw new TimeoutException();

                    if (!socket.Connected)
                        await ConnectSocket(socket);
                    
                    return socket;
                }
            }
            catch(TimeoutException)
            {
                _connections.Enqueue(CreateSocket());

                throw;
            }
            catch (System.Exception)
            {
                _connections.Enqueue(CreateSocket());

                continue;
            }

            // Task.Delay(1000, cts.Token).Wait();
            // catch (AggregateException ae) when (ae.InnerExceptions.All(e => e is TaskCanceledException))
            // {
            //     return null;
            // }
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

    private async Task ConnectSocket(Socket socket)
    {
        // 부하가 심할 것인데?
        var delayTask = Task.Delay(TimeSpan.FromMilliseconds(1));
        var connectTask = socket.ConnectAsync(_endPoint);
        var completedTask = await Task.WhenAny(delayTask, connectTask);

        if (completedTask == delayTask)
            throw new TimeoutException();

        await connectTask;
    }
}