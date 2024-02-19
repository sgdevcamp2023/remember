using System.Net.Sockets;
using System.Timers;
using Timer = System.Timers.Timer;

namespace SmileGatewayCore.Instance.Upstream;

public partial class EndPoint : NetworkInstance
{
    private Timer _timer = new Timer();
    private int _defaultTime = 1000;
    public bool IsAlive { get; private set; } = true;
    private async void OnTimerEvent(object? sender, ElapsedEventArgs args)
    {
        // 연결 체크
        try
        {
            Socket socket = _connectPool.CreateSocket();
            await _connectPool.ConnectAsync(socket, IpEndPoint, _connectTimeout);
            _connectPool.EnqueueSocket(socket);
            _connectPool.AddAliveCount();
            
            IsAlive = true;
        }
        catch (System.Exception e)
        {
            System.Console.WriteLine(e.Message);
        }

        if(!IsAlive)
        {
            if (_timer.Interval > _defaultTime * 60)
                _timer.Interval = _defaultTime * 60;
            else
                _timer.Interval = _timer.Interval * 2;
            _timer.Enabled = true;
        }
    }

    public override async void Init()
    {
        _timer.Elapsed += OnTimerEvent;
        _timer.Interval = _defaultTime;
        _timer.AutoReset = false;

        await _connectPool.Init(IpEndPoint, _connectTimeout);
    }
}