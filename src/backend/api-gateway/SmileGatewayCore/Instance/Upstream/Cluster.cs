using SmileGatewayCore.Config;
using SmileGatewayCore.Http.Context;

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
    private ClusterFilterChains _filterChains = new ClusterFilterChains();
    private List<EndPoint> endPoints = new List<EndPoint>();
    public readonly ClusterConfig Config;

    public Cluster(ClusterConfig config)
    {
        Config = config;

        foreach (AddressConfig address in config.Address)
        {
            endPoints.Add(new EndPoint(address));
        }

        if (Config.CustomFilters != null)
        {
            foreach (CustomFilter filter in Config.CustomFilters)
            {
                _filterChains.UseFilter(filter.Name);
            }
        }

        _filterChains.Init();
    }
    public async Task Run(HttpContext context)
    {
        EndPoint endPoint = GetEndPoint();
        await _filterChains.FilterStartAsync(endPoint, context);
    }

    public Cluster Clone()
    {
        return new Cluster(Config);
    }

    public EndPoint GetEndPoint()
    {
        long min = int.MaxValue;
        EndPoint endPoint = null!;

        foreach (EndPoint point in endPoints)
        {
            long count = point.GetUsingCount();
            if (count < min)
            {
                min = count;
                endPoint = point;
            }
        }

        return endPoint;
    }
}