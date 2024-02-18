using Microsoft.AspNetCore.Authentication.OAuth.Claims;
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
        UseFilter<ExceptionFilter>();
        UseFilter<ServiceFilter>();
        UseFilter<TraceFilter>();
        UseFilter<OriginalFilter>();
        // UseFilter<AuthorizationFilter>();
        UseFilter<LogFilter>();
    }

    public void UseFilter(string filterName)
    {
        Type? type = Type.GetType(filterName + ", SmileGateway");
        if (type == null)
            throw new System.Exception();

        if (typeof(IListenerFilterBase).IsAssignableFrom(type) == false)
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
            return async (instance, context) =>
             {
                 var filter = Activator.CreateInstance(type) as IListenerFilterBase;

                 if (filter == null)
                 {
                     throw new System.Exception();
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
    // 처리해야될게, HttpContext를 Filter로 만들어서 넘길 것인가?
    // 아니면 Listener에서 처리해서 넘길 것인가?
}