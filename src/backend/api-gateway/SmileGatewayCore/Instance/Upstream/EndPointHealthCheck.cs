using System.Net.Sockets;
using System.Timers;
using Timer = System.Timers.Timer;

namespace SmileGatewayCore.Instance.Upstream;

public partial class EndPoint : NetworkInstance
{
    private Timer _timer = new Timer();
    private int _defaultTime = 1000;
    private bool IsHealthCheck { get; set; } = false;
    public bool IsAlive { get; private set; } = true;
    private async void OnTimerEvent(object? sender, ElapsedEventArgs args)
    {
        List<Socket> deadSockets = new List<Socket>();
        // 연결 체크
        while (true)
        {
            Socket? socket = _connectPool.GetDeadSocket();
            if (socket == null)
                break;

            socket = _connectPool.CreateSocket();
            
            try
            {
                await _connectPool.ConnectAsync(socket, IpEndPoint, _connectTimeout);

                _connectPool.EnqueueAliveSocket(socket);
                IsAlive = true;
            }
            catch (System.Exception e)
            {
                System.Console.WriteLine(e.Message);
                deadSockets.Add(socket);
            }
        }

        foreach(var socket in deadSockets)
        {
            _connectPool.EnqueueDeadSocket(socket);
        }

        System.Console.WriteLine("Dead Count : " + _connectPool.DeadCount);
        if (_connectPool.DeadCount != 0)
        {
            if (_timer.Interval > _defaultTime * 60)
                    _timer.Interval = _defaultTime * 60;
                else
                    _timer.Interval = _timer.Interval * 2;
            _timer.Enabled = true;
        }
        else
            IsHealthCheck = false;
    }

    public override async void Init()
    {
        _timer.Elapsed += OnTimerEvent;
        _timer.Interval = _defaultTime;
        _timer.AutoReset = false;

        await _connectPool.Init(IpEndPoint, _connectTimeout);
    }
}