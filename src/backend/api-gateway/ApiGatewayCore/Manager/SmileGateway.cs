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
        // 초기화 
    }
}