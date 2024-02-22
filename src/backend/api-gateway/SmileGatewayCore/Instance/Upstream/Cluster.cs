using SmileGatewayCore.Config;
using SmileGatewayCore.Exception;
using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Manager;

namespace SmileGatewayCore.Instance.Upstream;

/// <summary>
/// 1. 필터
/// 2. 로드 밸런서
/// 3. 업 스트림 전달
/// 4. 클러스터 동기화
/// 5. Queue로 동기화
/// </summary>
internal class Cluster
{
    private UpStreamFilterChains _filterChains = new UpStreamFilterChains();
    private List<EndPoint> endPoints = new List<EndPoint>();
    public ClusterConfig Config { get; private set; } = null!;

    public Cluster(ClusterConfig config)
    {
        Config = config;

        foreach (AddressConfig address in config.Address)
        {
            EndPoint endPoint = new EndPoint(address, Config.ConnectTimeout, Config.RequestTimeout);
            // EndPointManager.Instance.Add
            endPoints.Add(endPoint);
        }

        if (Config.CustomFilters != null)
        {
            foreach (CustomFilterConfig filter in Config.CustomFilters)
            {
                _filterChains.UseFilter(filter.Name);
            }
        }

        _filterChains.Init();
    }

    public async Task Run(HttpContext context)
    {
        EndPoint? endPoint = GetEndPoint();
        if(endPoint == null)
            throw new NetworkException(3200);

        await _filterChains.FilterStartAsync(endPoint, context);
    }

    public EndPoint? GetEndPoint()
    {
        long min = int.MaxValue;
        EndPoint? endPoint = null;

        foreach (EndPoint point in endPoints)
        {
            if(!point.IsAlive)
                continue;
            
            long count = point.GetUsingCount();
            if (count < min)
            {
                min = count;
                endPoint = point;
            }
        }

        return endPoint;
    }

    public void ChangedCluster(ClusterConfig config)
    {
        // 클러스터 동기화
        
    }

    public void DeleteCluster()
    {
        // 클러스터 삭제
    }
}