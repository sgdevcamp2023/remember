using SmileGatewayCore.Filter.Cluster;
using SmileGatewayCore.Http.Context;

namespace SmileGatewayCore.Instance.Upstream;

public class ClusterFilterChains : IClusterFilterChains
{
    private List<Func<ClusterDelegate, ClusterDelegate>> _filters = new List<Func<ClusterDelegate, ClusterDelegate>>();

    public void Init()
    {
        // UseFilter<TraceFilter>();
        // UseFilter<LogFilter>();
    }

    public void UseFilter(string filterName)
    {
        Type? type = Type.GetType(filterName+ ", SmileGateway");
        if (type == null)
            throw new Exception();

        if (type.IsSubclassOf(typeof(IClusterFilterBase)) == false)
            throw new Exception();

        UseFilter(type);
    }

    public void UseFilter<T>()
    {
        UseFilter(typeof(T));
    }

    public void UseFilter(Type type)
    {
        Use(next =>
        {
            return async (context) =>
             {
                 var filter = Activator.CreateInstance(type) as IClusterFilterBase;

                 if (filter == null)
                 {
                     throw new InvalidOperationException("filter is null");
                 }

                 await filter.InvokeAsync(context, next);
             };
        });
    }
    public void Use(Func<ClusterDelegate, ClusterDelegate> filter)
    {
        _filters.Add(filter);
    }

    // 무조건 RouteFilter가 마지막
    public async Task FilterStartAsync(EndPoint endPoint, HttpContext context)
    {
        ClusterDelegate last = async (context) =>
        {
            await endPoint.StartAsync(context);
        };

        for (int i = _filters.Count - 1; i >= 0; i--)
        {
            last = _filters[i](last);
        }

        await last(context);
    }
}