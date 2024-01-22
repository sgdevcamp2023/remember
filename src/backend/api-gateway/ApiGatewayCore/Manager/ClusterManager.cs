using ApiGatewayCore.Config;

namespace ApiGatewayCore.Manager;

public class ClusterManager
{
    private List<ClusterModel> _clusterModel = null!;
    public void Init(List<ClusterModel> model)
    {
        _clusterModel = model;
    }
}