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
        _capacity = capacity;
        _endPoint = endPoint;

        Init(_capacity);
    }

    private void Init(int capacity)
    {
        for (int i = 0; i < capacity; i++)
        {
            // 설정 통해서 커넥션 유지
            try
            {
                Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                socket.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReuseAddress, true);
                socket.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.KeepAlive, true);

                _connections.Enqueue(socket);
            }
            catch (SocketException e)
            {
                System.Console.WriteLine(e.Message);
            }
        }
    }
    public Socket? RentSocket()
    {
        // CancellationTokenSource cts = new CancellationTokenSource();
        // cts.CancelAfter(TimeSpan.FromSeconds(1));

        // 타임 제한을 건다?
        while (true)
        {
            try
            {
                if (_connections.TryDequeue(out Socket? socket))
                {
                    if (!socket.Connected)
                        socket.Connect(_endPoint);
                    return socket;
                }

                // Task.Delay(1000, cts.Token).Wait();
            }
            // catch (AggregateException ae) when (ae.InnerExceptions.All(e => e is TaskCanceledException))
            // {
            //     return null;
            // }
            catch (System.Exception e)
            {
                System.Console.WriteLine(e.Message);
                return null;
            }
        }
    }

    public void ReturnSocket(Socket socket)
    {
        _connections.Enqueue(socket);
    }
}