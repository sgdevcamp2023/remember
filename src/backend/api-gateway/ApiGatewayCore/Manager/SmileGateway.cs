using ApiGatewayCore.Config;

namespace ApiGatewayCore.Manager;

public class SmileGateway
{
    internal ListenerManager ListenerManager { get; private set; } = new();
    internal ClusterManager ClusterManager { get; private set; } = new();
    private ConfigReader _configReader;

    public SmileGateway(string configPath)
    {
        _configReader = new ConfigReader(configPath);
    }

    public void Init()
    {
        // 초기화 
        Root config = _configReader.Load<Root>();
        ClusterManager.Init(config.Clusters);
        ListenerManager.ClusterManager = ClusterManager;

        ListenerManager.Init(config.Listeners);
        ClusterManager.ListenerManager = ListenerManager;
    }

    public void Run()
    {
        foreach (var listener in ListenerManager.Listeners)
        {
            listener.Run();
        }
        System.Console.WriteLine("ApiGatewayCore is running...");
        while (true)
        {
            Thread.Sleep(1000);
        }
    }
}