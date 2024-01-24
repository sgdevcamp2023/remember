using System.Net.Sockets;

namespace ApiGatewayCore.Instance;

public interface INetwork
{
    public void Init();

    public void Send(Socket socket, ArraySegment<byte> buffer);
    public void Receive(Socket socket);
    public void Disconnect(Socket socket);
}