using System.Net.Sockets;
using SmileGatewayCore.Exception;
using SmileGatewayCore.Utils;

namespace SmileGatewayCore.Instance;

public abstract class NetworkInstance : INetworkInstance
{
    protected MemoryPool _memory = new MemoryPool();
    protected TimeSpan _timeout = TimeSpan.FromSeconds(5);

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
        ArraySegment<byte> buffer = _memory.RentDefaultBytes();
        await ProcessReceiveAsync(socket, buffer);

        // 수거
        _memory.ReturnBytes(buffer);
    }

    private async Task ProcessReceiveAsync(Socket socket, ArraySegment<byte> buffer)
    {
        if (!socket.Connected)
            return;

        try
        {
            var recvTask = socket.ReceiveAsync(buffer, SocketFlags.None);
            var delayTask = Task.Delay(_timeout);
            var completedTask = await Task.WhenAny(delayTask, recvTask);

            if (completedTask == recvTask)
            {
                int recvLen = await recvTask;
                if (recvLen <= 0)
                    throw new SocketException((int)SocketError.ConnectionReset);

                await OnReceive(socket, buffer, recvLen);
            }
            else
                throw new TimeoutException();
        }
        catch (System.Exception e)
        {
            System.Console.WriteLine(e.Message);
            await Disconnect(socket);
            throw new NetworkException(3200);
        }
    }

    private async Task ProcessSendAsync(Socket socket, ArraySegment<byte> data)
    {
        if (!socket.Connected)
            return;

        try
        {
            var sendTask = socket.SendAsync(data, SocketFlags.None);
            var delayTask = Task.Delay(_timeout);
            var completedTask = await Task.WhenAny(delayTask, sendTask);

            if (completedTask == delayTask)
                throw new TimeoutException();

            int sendLen = await sendTask;
            if (sendLen <= 0)
                throw new SocketException((int)SocketError.ConnectionReset);

            await OnSend(socket, sendLen);
        }
        catch (System.Exception e)
        {
            System.Console.WriteLine(e.Message);
            await Disconnect(socket);
            throw new NetworkException(3200);
        }
    }

    public async Task Disconnect(Socket socket)
    {
        try
        {
            System.Console.WriteLine("Disconnect");
            socket.Shutdown(SocketShutdown.Both);

            await socket.DisconnectAsync(reuseSocket: true);
        }
        catch (System.Exception)
        {
            throw new NetworkException(3200);
        }
    }
}