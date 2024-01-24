using ApiGatewayCore.Config;

namespace ApiGatewayCore.Manager;

public class ClusterManager
{
    private List<ClusterConfig> _clusterModel = null!;
    public void Init(List<ClusterConfig> model)
    {
        _clusterModel = model;
    }
}