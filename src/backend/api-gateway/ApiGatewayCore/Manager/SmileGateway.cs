using ApiGatewayCore.Config;

namespace ApiGatewayCore.Manager;

public class SmileGateway
{
    public ListenerManager ListenerManager { get; } = new();
    public ClusterManager ClusterManager { get; } = new();
    private ConfigReader _configReader = new ConfigReader("config.yaml");
    public SmileGateway()
    {
        
    }

    public void Init()
    {
        Root config = _configReader.Load<Root>();
        ListenerManager.Init(config.Listeners);
        ClusterManager.Init(config.Clusters);
        // 초기화 
    }

    public void Run()
    {
        // 실행
    }
}