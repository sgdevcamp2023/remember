using System.Net.Sockets;

namespace ApiGatewayCore.Instance;

internal interface IListener
{
    public void Init();
    public void RegisterAccept(SocketAsyncEventArgs args);
    public void OnAcceptCompleted(object? sender, SocketAsyncEventArgs args);
}