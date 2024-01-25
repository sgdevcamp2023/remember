using System.Net.Sockets;

namespace ApiGatewayCore.Instance;

internal interface INetworkInstance
{
    public void Send(Socket socket, byte[] data);
    public void Receive(Socket socket);
    public void Disconnect(Socket socket);
}