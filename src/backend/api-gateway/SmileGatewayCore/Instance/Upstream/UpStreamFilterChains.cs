using SmileGatewayCore.Filter.Cluster;
using SmileGatewayCore.Http.Context;

namespace SmileGatewayCore.Instance.Upstream;

public delegate Task UpStreamDelegate(HttpContext context);

public class UpStreamFilterChains : IFilterChain<UpStreamDelegate, EndPoint>
{
    private List<Func<UpStreamDelegate, UpStreamDelegate>> _filters = new List<Func<UpStreamDelegate, UpStreamDelegate>>();
    private UpStreamDelegate? _start = null;
    private AsyncLocal<EndPoint> _endPoint = new AsyncLocal<EndPoint>();
    public void Init()
    {
        // UseFilter<ClusterExceptionFilter>();
    }


    public void UseFilter(string filterName)
    {
        Type? type = Type.GetType(filterName + ", SmileGateway");
        if (type == null)
            throw new System.Exception();

        if (typeof(IUpStreamFilterBase).IsAssignableFrom(type) == false)
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
                 var filter = Activator.CreateInstance(type) as IUpStreamFilterBase;

                 if (filter == null)
                 {
                     throw new InvalidOperationException("filter is null");
                 }

                 await filter.InvokeAsync(context, next);
             };
        });
    }
    public void Use(Func<UpStreamDelegate, UpStreamDelegate> filter)
    {
        _filters.Add(filter);
    }

    // 무조건 RouteFilter가 마지막
    public async Task FilterStartAsync(EndPoint endPoint, HttpContext context)
    {
        _endPoint.Value = endPoint;

        if (_start == null)
        {
            UpStreamDelegate last = async (context) =>
            {
                try
                {
                    await _endPoint.Value.StartAsync(context);
                }
                catch (System.Exception)
                {
                    // 혹시나 터지는 경우를 대비하여
                    _endPoint.Value.DecreaseUsingCount();
                    throw;
                }
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