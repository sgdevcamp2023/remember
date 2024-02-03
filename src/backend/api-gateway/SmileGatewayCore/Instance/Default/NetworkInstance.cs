using System.Net.Sockets;
using SmileGatewayCore.Exception;
using SmileGatewayCore.Utils;

namespace SmileGatewayCore.Instance;

public abstract class NetworkInstance : INetworkInstance
{
    protected MemoryPool _memory = new MemoryPool(8192);
    protected SocketPool _socketPool = new SocketPool();

    #region Abstract
    public abstract void Init();
    protected abstract Task OnReceive(Socket socket, ArraySegment<byte> buffer, int size);
    protected abstract Task OnSend(Socket socket, int size);
    #endregion

    public Task Send(Socket socket, byte[] data)
    {
        return ProcessSendAsync(socket, data);
    }

    public Task Receive(Socket socket)
    {
        return ProcessReceiveAsync(socket);
    }

    public Task Receive(Socket socket, ArraySegment<byte> buffer)
    {
        return ProcessReceiveAsync(socket, buffer);
    }

    private async Task ProcessReceiveAsync(Socket socket)
    {
        ArraySegment<byte> buffer = _memory.RentBytes();

        await ProcessReceiveAsync(socket, buffer);

        // 수거
        _memory.ReturnBytes(buffer);
    }

    private async Task ProcessReceiveAsync(Socket socket, ArraySegment<byte> buffer)
    {
        try
        {
            int recvLen = await socket.ReceiveAsync(buffer, SocketFlags.None);
            if (recvLen <= 0)
                throw new SocketException((int)SocketError.ConnectionReset);

            await OnReceive(socket, buffer, recvLen);
        }
        catch (System.Exception e)
        {
            System.Console.WriteLine(e.Message);
            Disconnect(socket);
        }
    }

    private async Task ProcessSendAsync(Socket socket, ArraySegment<byte> data)
    {
        try
        {
            int sendLen = await socket.SendAsync(data, SocketFlags.None);
            if (sendLen <= 0)
                throw new SocketException((int)SocketError.ConnectionReset);

            await OnSend(socket, sendLen);
        }
        catch (System.Exception e)
        {
            System.Console.WriteLine(e.Message);
            Disconnect(socket);
        }
    }

    public void Disconnect(Socket socket)
    {
        if(socket.Connected)
            socket.Disconnect(reuseSocket: true);
    }
}