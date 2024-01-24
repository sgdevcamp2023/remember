using System.Net.Sockets;
using ApiGatewayCore.Filter;
using ApiGatewayCore.Filter.Listner;
using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Utils;

namespace ApiGatewayCore.Instance;

public abstract class DefaultInstance : IFilter, INetwork
{
    #region Filter
    protected List<Func<RequestDelegate, RequestDelegate>> _filters = new List<Func<RequestDelegate, RequestDelegate>>();
    private RequestDelegate? _start = null;
    public void UseFilter<T>()
    {
        UseFilter(typeof(T));
    }
    public void UseFilter(Type type)
    {
        Use(next =>
        {
            return async context =>
             {
                 var middleware = Activator.CreateInstance(type) as IFilterBase;

                 if (middleware == null)
                 {
                     throw new InvalidOperationException("middleware is null");
                 }

                 await middleware.InvokeAsync(context, next);
             };
        });
    }
    public void Use(Func<RequestDelegate, RequestDelegate> filter)
    {
        _filters.Add(filter);
    }

    // 무조건 RouteFilter가 마지막
    public void UseLastFilter()
    {
        RequestDelegate last = async context =>
        {
            RouteFilter filter = new RouteFilter();
            await filter.InvokeAsync(context);
        };

        for (int i = _filters.Count - 1; i >= 0; i--)
        {
            last = _filters[i](last);
        }

        _start = last;
    }

    public void FilterStart(HttpContext context)
    {
        if(_start == null)
            return;

        _start(context);
    }
    #endregion

    #region Network
    private Queue<SocketAsyncEventArgs> _recvArgs = new Queue<SocketAsyncEventArgs>();
    private Queue<SocketAsyncEventArgs> _sendArgs = new Queue<SocketAsyncEventArgs>();
    private MemoryPool _memory = new MemoryPool(1024);
    
    #region Abstract
    public abstract void Init();
    public abstract Task Run();
    protected abstract void OnReceive(Socket socket, ArraySegment<byte> buffer);
    protected abstract void OnSend(Socket socket, ArraySegment<byte> buffer);
    #endregion
    
    public void Send(Socket socket, ArraySegment<byte> buffer)
    {
        RegisterSend(socket, buffer);
    }

    public void Receive(Socket socket)
    {
        RegisterReceive(socket);
    }

    private void RegisterReceive(Socket socket)
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

    private void OnReceiveCompleted(object? sender, SocketAsyncEventArgs args)
    {
        if (args.SocketError == SocketError.Success)
        {
            Socket? socket = args.AcceptSocket;
            if (socket == null)
                throw new Exception();

            if (args.BytesTransferred < 0)
            {
                Disconnect(socket);
                return;
            }

            ArraySegment<byte> buffer = args.Buffer!;

            OnReceive(socket, args.Buffer!);

            // 수거
            _memory.Enqueue(buffer);
            _recvArgs.Enqueue(args);
        }
        else
            throw new Exception();
    }

    private void RegisterSend(Socket socket, ArraySegment<byte> buffer)
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
            sendArgs.SetBuffer(buffer);
            sendArgs.Completed += new EventHandler<SocketAsyncEventArgs>(OnSendCompleted);
        }

        sendArgs.AcceptSocket = socket;

        bool isPending = socket.SendAsync(sendArgs);
        if (!isPending)
            OnSendCompleted(null, sendArgs);
    }

    private void OnSendCompleted(object? sender, SocketAsyncEventArgs args)
    {
        if (args.SocketError == SocketError.Success)
        {
            Socket? socket = args.AcceptSocket;
            if (socket == null)
                throw new Exception();

            ArraySegment<byte> buffer = args.Buffer!;
            OnSend(socket, buffer);

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
    #endregion
}