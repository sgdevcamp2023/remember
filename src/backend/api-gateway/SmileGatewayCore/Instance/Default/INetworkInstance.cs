using System.Net.Sockets;

namespace SmileGatewayCore.Instance;

internal interface INetworkInstance
{
    public Task Send(Socket socket, byte[] data);
    public Task Receive(Socket socket);
    public Task Receive(Socket socket, ArraySegment<byte> buffer);
    public Task Disconnect(Socket socket);
}