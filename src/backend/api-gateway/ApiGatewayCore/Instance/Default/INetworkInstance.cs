using System.Net.Sockets;

namespace ApiGatewayCore.Instance;

internal interface INetworkInstance
{
    public Task Send(Socket socket, byte[] data);
    public Task Receive(Socket socket);
    public void Disconnect(Socket socket);
}