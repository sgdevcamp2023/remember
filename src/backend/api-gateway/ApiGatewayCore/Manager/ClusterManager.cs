using ApiGatewayCore.Config;
using ApiGatewayCore.Instance.Cluster;

namespace ApiGatewayCore.Manager;

public class ClusterManager
{
    private List<Cluster> _clusters = new List<Cluster>();
    public void Init(List<ClusterConfig> model)
    {
        foreach(var config in model)
        {
            var cluster = new Cluster(config);
            cluster.Init();

            _clusters.Add(cluster);
        }
    }
}