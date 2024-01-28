using System.Collections.Concurrent;
using System.Net.Sockets;

namespace SmileGatewayCore.Instance.Upstream;

// 연결 관리는 어떻게?
public class ConnectionPool
{
    private ConcurrentBag<Socket> _connections = new ConcurrentBag<Socket>();

    public Socket RentSocket()
    {
        if(_connections.TryTake(out Socket? socket))
            return socket;
        else
            return new Socket(SocketType.Stream, ProtocolType.Tcp);
    }

    public void ReturnSocket(Socket socket)
    {
        socket.Disconnect(false);
        _connections.Add(socket);
    }
}