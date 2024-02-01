using System.Collections.Concurrent;
using System.Net;
using System.Net.Sockets;

namespace SmileGatewayCore.Instance.Upstream;

// 연결 관리는 어떻게?
public class ConnectionPool
{
    private ConcurrentQueue<Socket> _connections = new ConcurrentQueue<Socket>();

    public ConnectionPool(int capacity, IPEndPoint endPoint)
    {
        Init(capacity, endPoint);
    }

    private void Init(int capacity, IPEndPoint endPoint)
    {

        for (int i = 0; i < capacity; i++)
        {
            // 설정 통해서 커넥션 유지
            Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            try
            {
                socket.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReuseAddress, true);
                socket.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.KeepAlive, true);
            }
            catch (SocketException e)
            {
                System.Console.WriteLine(e.Message);
            }

            // socket.Connect(endPoint);
            _connections.Enqueue(socket);
        }
    }
    public Socket? RentSocket()
    {
        CancellationTokenSource cts = new CancellationTokenSource();
        cts.CancelAfter(TimeSpan.FromSeconds(1));

        while (true)
        {
            if (_connections.TryDequeue(out Socket? socket))
            {
                return socket;
            }

            try
            {
                Task.Delay(1000, cts.Token).Wait();
            }
            catch (AggregateException ae) when (ae.InnerExceptions.All(e => e is TaskCanceledException))
            {
                return null;
            }
        }
    }

    public void ReturnSocket(Socket socket)
    {
        socket.Disconnect(reuseSocket: true);
        _connections.Enqueue(socket);
    }
}