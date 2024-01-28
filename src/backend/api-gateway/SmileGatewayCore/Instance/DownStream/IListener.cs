using System.Net.Sockets;

namespace SmileGatewayCore.Instance.DownStream;

internal interface IListener
{
    public void Init();
    public void RegisterAccept(SocketAsyncEventArgs args);
    public void OnAcceptCompleted(object? sender, SocketAsyncEventArgs args);
}