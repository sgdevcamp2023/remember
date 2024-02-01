using System.Net.Sockets;
using SmileGatewayCore.Utils;

namespace SmileGatewayCore.Instance;

public abstract class NetworkInstance : INetworkInstance
{
    protected MemoryPool _memory = new MemoryPool(8192);

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

    public Task Receive(Socket socket, ArraySegment<byte> buffer)
    {
        return ProcessReceiveAsync(socket, buffer);
    }

    private async Task ProcessReceiveAsync(Socket socket)
    {
        if (!socket.Connected)
            throw new Exception();

        ArraySegment<byte> buffer = _memory.RentBytes();

        try
        {
            int recvLen = await socket.ReceiveAsync(buffer, SocketFlags.None);
            if (recvLen < 0)
            {
                Disconnect(socket);
                throw new Exception();
            }

            OnReceive(socket, buffer, recvLen);
        }
        catch (Exception e)
        {
            System.Console.WriteLine(e.Message);
            Disconnect(socket);
        }

        // 수거
        _memory.ReturnBytes(buffer);
    }

    private async Task ProcessReceiveAsync(Socket socket, ArraySegment<byte>? buffer)
    {
        if (!socket.Connected)
            throw new Exception();
        if(buffer == null)
            buffer = _memory.RentBytes();

        try
        {
            int recvLen = await socket.ReceiveAsync(buffer.Value, SocketFlags.None);
            if (recvLen < 0)
            {
                Disconnect(socket);
                throw new Exception();
            }

            OnReceive(socket, buffer.Value, recvLen);
        }
        catch (Exception e)
        {
            System.Console.WriteLine(e.Message);
            Disconnect(socket);
        }
    }

    private async Task ProcessSendAsync(Socket socket, ArraySegment<byte> data)
    {
        if (!socket.Connected)
            throw new Exception();

        try
        {
            int sendLen = await socket.SendAsync(data, SocketFlags.None);
            if (sendLen < 0)
            {
                Disconnect(socket);
                throw new Exception();
            }

            OnSend(socket, sendLen);
        }
        catch (Exception e)
        {
            System.Console.WriteLine(e.Message);
            Disconnect(socket);
        }
    }

    public void Disconnect(Socket socket)
    {
        socket.Disconnect(false);
        // _memory.ReturnSocket(socket);
    }
}