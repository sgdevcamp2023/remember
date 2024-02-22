using SmileGatewayCore.Config;
using SmileGatewayCore.Exception;
using SmileGatewayCore.Instance.Upstream;

namespace SmileGatewayCore.Instance.DownStream;

internal partial class Listener : NetworkInstance
{
    public void Changed(ListenerConfig config)
    {
        SyncCluster(config.RouteConfig.Clusters);

    }

    private void SyncCluster(List<string> clusters)
    {
        // 설정에서 사라진 클러스터 삭제
        // Except : 차집합으로 clusters에는 있지만 _clusters에는 없는 서비스를 찾아내서 리스트로 반환
        _clusters.Keys.Except(clusters).ToList().ForEach(x =>
        {
            _clusters.TryRemove(x, out Cluster? cluster);
            // 클러스터를 정리하여 삭제하는 과정이 필요함 
            if (cluster != null)
                cluster.DeleteCluster();
        });

        // 새로운 클러스터 추가
        // 새로운 얘는 업데이트?
        foreach (string clusterName in clusters)
        {
            if (_clusterManager.Clusters.TryGetValue(clusterName, out ClusterConfig? clusterConfig))
            {
                
            }
            else
            {
                throw new ConfigException(3105);
            }
        };
    }
}