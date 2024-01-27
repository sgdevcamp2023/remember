using System.Net.Sockets;
using System.Security.Cryptography;
using System.Security.Principal;
using ApiGatewayCore.Utils;

namespace ApiGatewayCore.Instance;

public abstract class NetworkInstance : INetworkInstance
{
    private MemoryPool _memory = new MemoryPool(1024);
    
    #region Abstract
    public abstract void Init();
    protected abstract void OnReceive(Socket socket, ArraySegment<byte> buffer, int size);
    protected abstract void OnSend(Socket socket, int size);
    #endregion
    
    public Task Send(Socket socket, byte[] data)
    {
        return ProcessSendAsync(socket, data);
    }

    public Task Receive(Socket socket)
    {
        return ProcessReceiveAsync(socket);
    }

    private async Task ProcessReceiveAsync(Socket socket)
    {
        if(!socket.Connected)
            throw new Exception();

        ArraySegment<byte> buffer = _memory.RentBytes();
        int recvLen = await socket.ReceiveAsync(buffer, SocketFlags.None);
        if(recvLen < 0)
        {
            Disconnect(socket);
            throw new Exception();
        }

        OnReceive(socket, buffer, recvLen);

        // 수거
        _memory.ReturnBytes(buffer);
    }

    private async Task ProcessSendAsync(Socket socket, ArraySegment<byte> data)
    {
        if(!socket.Connected)
            throw new Exception();
        
        int sendLen = await socket.SendAsync(data, SocketFlags.None);
        if(sendLen < 0)
        {
            Disconnect(socket);
            throw new Exception();
        }

        OnSend(socket, sendLen);
    }

    public void Disconnect(Socket socket)
    {
        socket.Disconnect(false);
    }
}