using System.Net.Sockets;
using SmileGatewayCore.Exception;
using SmileGatewayCore.Utils;

namespace SmileGatewayCore.Instance;

public abstract class NetworkInstance : INetworkInstance
{
    protected MemoryPool _memory = new MemoryPool(8192);
    protected SocketPool _socketPool = new SocketPool();
    private TimeSpan _timeout = TimeSpan.FromSeconds(1);
    
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
            var delayTask = Task.Delay(_timeout);
            var recvTask = socket.ReceiveAsync(buffer, SocketFlags.None);
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
            Disconnect(socket);
        }
    }

    private async Task ProcessSendAsync(Socket socket, ArraySegment<byte> data)
    {
        try
        {
            var delayTask = Task.Delay(_timeout);
            var sendTask = socket.SendAsync(data, SocketFlags.None);
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
            Disconnect(socket);
        }
    }

    public void Disconnect(Socket socket)
    {
        try
        {
            socket.Disconnect(reuseSocket: true);
        }
        catch (System.Exception e)
        {
            System.Console.WriteLine(e.Message);
        }
    }
}