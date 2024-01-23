using System.Net.Sockets;

namespace ApiGatewayCore.Instance.Listener;

public interface IListener
{
    public void Init();
    public void RegisterAccept(SocketAsyncEventArgs args);
    public void OnAcceptCompleted(object? sender, SocketAsyncEventArgs args);

    public void RegisterReceive(Socket socket);
    public void OnReceiveCompleted(object? sender, SocketAsyncEventArgs args);

    public void RegisterSend(Socket socket);
    public void OnSendCompleted(object? sender, SocketAsyncEventArgs args);

    public void Disconnect(Socket socket);

}