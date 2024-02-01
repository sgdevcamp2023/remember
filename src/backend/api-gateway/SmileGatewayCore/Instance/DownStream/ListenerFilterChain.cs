using SmileGatewayCore.Filter.Listner;
using SmileGatewayCore.Http.Context;

namespace SmileGatewayCore.Instance.DownStream;

public delegate Task ListenerDelegate(Adapter adapter, HttpContext context);

public class ListenerFilterChains : IFilterChain<ListenerDelegate, Adapter>
{
    protected List<Func<ListenerDelegate, ListenerDelegate>> _filters = new List<Func<ListenerDelegate, ListenerDelegate>>();
    private ListenerDelegate? _start = null;
    public void Init()
    {
        // 인증 필터
        // UseFilter<ListenerExceptionFilter>();
        UseFilter<AuthorizationFilter>();
    }

    public void SetLastFilter()
    {

    }

    public void UseFilter(string filterName)
    {
        Type? type = Type.GetType(filterName + ", SmileGateway");
        if (type == null)
            throw new Exception();

        if (typeof(IListenerFilterBase).IsAssignableFrom(type) == false)
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
            return async (instance, context) =>
             {
                 var filter = Activator.CreateInstance(type) as IListenerFilterBase;

                 if (filter == null)
                 {
                     throw new Exception();
                 }

                 await filter.InvokeAsync(instance, context, next);
             };
        });
    }
    public void Use(Func<ListenerDelegate, ListenerDelegate> filter)
    {
        _filters.Add(filter);
    }

    // 무조건 RouteFilter가 마지막
    public async Task FilterStartAsync(Adapter adapter, HttpContext context)
    {
        if (_start == null)
        {
            SetLastFilter();

            ListenerDelegate last = async (adapter, context) =>
            {
                RouteFilter filter = new RouteFilter();
                await filter.InvokeAsync(adapter, context);
            };

            for (int i = _filters.Count - 1; i >= 0; i--)
            {
                last = _filters[i](last);
            }
            _start = last;
        }

        await _start(adapter, context);
    }
}