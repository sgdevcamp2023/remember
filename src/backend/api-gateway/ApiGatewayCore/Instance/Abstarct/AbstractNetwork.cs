using System.Net.Sockets;
using ApiGatewayCore.Utils;

namespace ApiGatewayCore.Instance;

public abstract class AbstractNetwork : AbstractFilter, INetwork
{
    private Queue<SocketAsyncEventArgs> _recvArgs = new Queue<SocketAsyncEventArgs>();
    private Queue<SocketAsyncEventArgs> _sendArgs = new Queue<SocketAsyncEventArgs>();
    private MemoryPool _memory = new MemoryPool(1024);
    
    public virtual void Init() { }

    public void RegisterReceive(Socket socket)
    {
        SocketAsyncEventArgs recvArgs = null!;
        if (_recvArgs.Count == 0)
        {
            recvArgs = _recvArgs.Dequeue();
        }
        else
        {
            recvArgs = new SocketAsyncEventArgs();
            recvArgs.SetBuffer(_memory.Dequeue());
            recvArgs.Completed += new EventHandler<SocketAsyncEventArgs>(OnReceiveCompleted);
        }

        recvArgs.AcceptSocket = socket;
        bool isPending = socket.ReceiveAsync(recvArgs);
        if (!isPending)
            OnReceiveCompleted(null, recvArgs);
    }

    public void OnReceiveCompleted(object? sender, SocketAsyncEventArgs args)
    {
        if (args.SocketError == SocketError.Success)
        {
            Socket? socket = args.AcceptSocket;
            if (socket == null)
                throw new Exception();

            if(args.BytesTransferred < 0)
            {
                Disconnect(socket);
                return;
            }

            ArraySegment<byte> buffer = args.Buffer!;
            
            OnReceive(args.Buffer!);

            // 수거
            _memory.Enqueue(buffer);
            _recvArgs.Enqueue(args);

            RegisterSend(socket);
        }
        else
            throw new Exception();
    }

    public void RegisterSend(Socket socket)
    {
        SocketAsyncEventArgs sendArgs = null!;
        // Cluster를 거쳐왔을 때 작동
        if (_sendArgs.Count == 0)
        {
            sendArgs = _sendArgs.Dequeue();

        }
        else
        {
            sendArgs = new SocketAsyncEventArgs();
            sendArgs.SetBuffer(_memory.Dequeue());
            sendArgs.Completed += new EventHandler<SocketAsyncEventArgs>(OnSendCompleted);
        }

        sendArgs.AcceptSocket = socket;

        bool isPending = socket.SendAsync(sendArgs);
        if (!isPending)
            OnSendCompleted(null, sendArgs);
    }

    public void OnSendCompleted(object? sender, SocketAsyncEventArgs args)
    {
        if(args.SocketError == SocketError.Success)
        {
            Socket? socket = args.AcceptSocket;
            if (socket == null)
                throw new Exception();

            ArraySegment<byte> buffer = args.Buffer!;
            OnSend(buffer);

            _memory.Enqueue(buffer);
            _sendArgs.Enqueue(args);
            
            Disconnect(socket);
        }
        else
            throw new Exception();
    }

    public void Disconnect(Socket socket)
    {
        socket.Disconnect(false);
    }

    protected abstract void OnReceive(ArraySegment<byte> buffer);
    protected abstract void OnSend(ArraySegment<byte> buffer);
    // public
}

