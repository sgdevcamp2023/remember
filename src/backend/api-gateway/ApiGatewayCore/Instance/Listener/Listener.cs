using System.Net;
using System.Net.Sockets;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Instance.Listener;

public class Listener : AbstractFilter, IListener
{
    private HttpContext _context = new();
    private Socket _listenerSocket = null!;
    private Queue<SocketAsyncEventArgs> _recvArgs = new Queue<SocketAsyncEventArgs>();
    private Queue<SocketAsyncEventArgs> _sendArgs = new Queue<SocketAsyncEventArgs>();
    public ListenerModel _model;
    public Listener(ListenerModel model)
    {
        _model = model;
    }
    public void Init()
    {
        _listenerSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        IPEndPoint endPoint = new IPEndPoint(IPAddress.Parse(_model.Address.Address), _model.Address.Port);

        _listenerSocket.Bind(endPoint);
        _listenerSocket.Listen(1);
    }

    public async Task Run()
    {
        for (int i = 0; i < _model.ThreadCount; i++)
        {
            SocketAsyncEventArgs args = new SocketAsyncEventArgs();
            args.Completed += new EventHandler<SocketAsyncEventArgs>(OnAcceptCompleted);
            RegisterAccept(args);
        }

        await Task.CompletedTask;
    }
    private void MakeHttpContext(string request)
    {
        _context.Request = new HttpRequest(request);
    }

    public void RegisterAccept(SocketAsyncEventArgs args)
    {
        args.AcceptSocket = null;
        bool isPending = _listenerSocket.AcceptAsync(args);
        if (!isPending)
            OnAcceptCompleted(null, args);
    }

    public void OnAcceptCompleted(object? sender, SocketAsyncEventArgs args)
    {
        if (args.SocketError == SocketError.Success)
        {
            Socket? socket = args.AcceptSocket;
            if (socket == null)
                throw new Exception();

            RegisterReceive(socket);
        }
        else
            throw new Exception();

        RegisterAccept(args);
    }

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
            recvArgs.SetBuffer(new byte[1024], 0, 1024);
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

            byte[]? bytes = args.Buffer;
            if (bytes == null)
                throw new Exception();

            MakeHttpContext(bytes.ToString()!);

            _recvArgs.Enqueue(args);
            // 이후 필터를 거쳐가는 과정이 필요

            // 필터 종료 후
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
            sendArgs.SetBuffer(new byte[1024], 0, 1024);
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
}