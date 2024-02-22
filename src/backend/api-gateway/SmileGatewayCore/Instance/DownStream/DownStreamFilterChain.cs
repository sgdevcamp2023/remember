using Microsoft.AspNetCore.Authentication.OAuth.Claims;
using SmileGatewayCore.Filter.Listner;
using SmileGatewayCore.Http.Context;

namespace SmileGatewayCore.Instance.DownStream;

public delegate Task DownStreamDelegate(Adapter adapter, HttpContext context);

public class DownStreamFilterChains : IFilterChain<DownStreamDelegate, Adapter>
{
    protected List<Func<DownStreamDelegate, DownStreamDelegate>> _filters = new List<Func<DownStreamDelegate, DownStreamDelegate>>();
    private DownStreamDelegate? _start = null;
    public void Init(bool isInside)
    {
        // 인증 필터
        UseFilter<ExceptionFilter>();

        if (!isInside)
        {
            UseFilter<ServiceFilter>();
            UseFilter<TraceFilter>();
            UseFilter<AuthorizationFilter>();
        }

        UseFilter<OriginalFilter>();        
    }

    public void UseFilter(string filterName)
    {
        Type? type = Type.GetType(filterName + ", SmileGateway");
        if (type == null)
            throw new System.Exception();

        if (typeof(IDownStreamFilterBase).IsAssignableFrom(type) == false)
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
                 var filter = Activator.CreateInstance(type) as IDownStreamFilterBase;

                 if (filter == null)
                 {
                     throw new System.Exception();
                 }

                 await filter.InvokeAsync(instance, context, next);
             };
        });
    }
    public void Use(Func<DownStreamDelegate, DownStreamDelegate> filter)
    {
        _filters.Add(filter);
    }

    // 무조건 RouteFilter가 마지막
    public async Task FilterStartAsync(Adapter adapter, HttpContext context)
    {
        if (_start == null)
        {
            UseFilter<LogFilter>();

            DownStreamDelegate last = async (adapter, context) =>
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