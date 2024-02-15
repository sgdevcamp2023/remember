using System.Collections.Concurrent;
using SmileGatewayCore.Config;
using SmileGatewayCore.Instance.Upstream;

namespace SmileGatewayCore.Manager;

internal class ClusterManager
{
    // 추가적인 동기 여부 확인해야함
    public ListenerManager ListenerManager { get; set; } = null!;
    public ConcurrentDictionary<string, ClusterConfig> Clusters { get; set; } = new ConcurrentDictionary<string, ClusterConfig>();

    public void Init(List<ClusterConfig> clusters)
    {
        foreach (var config in clusters)
        {
            Clusters.TryAdd(config.Name, config);
        }
    }

    public void Changed(List<ClusterConfig> clusters)
    {
        // 업데이트를 시켜줘야하는데, 어덯게 업데이트를 해야될까?
        foreach (var cluster in clusters)
        {
            Clusters.AddOrUpdate(cluster.Name, cluster, (key, oldvalue) => cluster);
        }
    }
}