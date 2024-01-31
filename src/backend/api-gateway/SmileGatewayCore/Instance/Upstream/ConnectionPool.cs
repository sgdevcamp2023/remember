using System.Collections.Concurrent;
using System.Net.Sockets;

namespace SmileGatewayCore.Instance.Upstream;

// 연결 관리는 어떻게?
public class ConnectionPool
{
    private ConcurrentBag<Socket> _connections = new ConcurrentBag<Socket>();

    public ConnectionPool(int capacity)
    {
        for (int i = 0; i < capacity; i++)
        {
            Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            _connections.Add(socket);
        }
    }
    public Socket? RentSocket()
    {
        CancellationTokenSource cts = new CancellationTokenSource();
        cts.CancelAfter(TimeSpan.FromSeconds(1));

        while (true)
        {
            if (_connections.TryTake(out Socket? socket))
                return socket;

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
        socket.Disconnect(false);
        _connections.Add(socket);
    }
}