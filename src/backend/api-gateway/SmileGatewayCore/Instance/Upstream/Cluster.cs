using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Instance.Upstream;

/// <summary>
/// 1. 필터
/// 2. 로드 밸런서
/// 3. 업 스트림 전달
/// 4. 클러스터 동기화
/// 5. Queue로 동기화
/// </summary>
internal class Cluster
{
    public readonly ClusterConfig config;

    public List<EndPoint> EndPoints = new List<EndPoint>();

    public Cluster(ClusterConfig config)
    {
        this.config = config;

        foreach(AddressConfig address in config.Address)
        {
            EndPoints.Add(new EndPoint(address));
        }
    }
    public async Task Run(HttpContext context)
    {
        EndPoint endPoint = GetEndPoint();
        await endPoint.StartAsync(context);
    }

    public Cluster Clone()
    {
        return new Cluster(config);
    }

    public EndPoint GetEndPoint()
    {
        long min = int.MaxValue;
        EndPoint endPoint = null!;

        foreach(EndPoint point in EndPoints)
        {
            long count = point.GetUsingCount();
            if(count < min)
            {
                min = count;
                endPoint = point;
            }
        }

        return endPoint;
    }
}