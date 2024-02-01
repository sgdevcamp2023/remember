using SmileGatewayCore.Filter.Cluster;
using SmileGatewayCore.Http.Context;

namespace SmileGatewayCore.Instance.Upstream;

public delegate Task ClusterDelegate(HttpContext context);

public class ClusterFilterChains : IFilterChain<ClusterDelegate, EndPoint>
{
    private List<Func<ClusterDelegate, ClusterDelegate>> _filters = new List<Func<ClusterDelegate, ClusterDelegate>>();
    private ClusterDelegate? _start = null;
    private AsyncLocal<EndPoint> _endPoint = new AsyncLocal<EndPoint>();
    public void Init()
    {
        UseFilter<ClusterExceptionFilter>();
    }
    

    public void UseFilter(string filterName)
    {
        Type? type = Type.GetType(filterName + ", SmileGateway");
        if (type == null)
            throw new System.Exception();

        if (typeof(IClusterFilterBase).IsAssignableFrom(type) == false)
            throw new System.Exception();

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
        _endPoint.Value = endPoint;

        if (_start == null)
        {
            ClusterDelegate last = async (context) =>
            {
                await _endPoint.Value.StartAsync(context);
            };

            for (int i = _filters.Count - 1; i >= 0; i--)
            {
                last = _filters[i](last);
            }

            _start = last;
        }

        await _start(context);
    }
}