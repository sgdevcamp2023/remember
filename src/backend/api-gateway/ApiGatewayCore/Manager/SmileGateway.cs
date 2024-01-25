using ApiGatewayCore.Config;
using ApiGatewayCore.Instance;

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
        ListenerManager.Init(config.Listeners);
        ClusterManager.Init(config.Clusters);

        ListenerManager.ClusterManager = ClusterManager;
        ClusterManager.ListenerManager = ListenerManager;
    }

    public void Run()
    {
        // 실행
    }
}