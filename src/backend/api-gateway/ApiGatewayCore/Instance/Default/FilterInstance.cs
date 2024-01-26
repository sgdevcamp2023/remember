using System.Net.Sockets;
using ApiGatewayCore.Filter;
using ApiGatewayCore.Filter.Listner;
using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Utils;

namespace ApiGatewayCore.Instance;

public abstract class FilterInstnace : IFilterInstance
{
    #region Abstract
    public abstract void Init();
    
    #endregion
    protected List<Func<RequestDelegate, RequestDelegate>> _filters = new List<Func<RequestDelegate, RequestDelegate>>();
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
                 var middleware = Activator.CreateInstance(type) as IFilterBase;

                 if (middleware == null)
                 {
                     throw new InvalidOperationException("middleware is null");
                 }

                 await middleware.InvokeAsync(instance, context, next);
             };
        });
    }
    public void Use(Func<RequestDelegate, RequestDelegate> filter)
    {
        _filters.Add(filter);
    }

    // 무조건 RouteFilter가 마지막
    public void FilterStart(Adapter adapter, HttpContext context)
    {
        RequestDelegate last = async (adapter, context) =>
        {
            RouteFilter filter = new RouteFilter();
            await filter.InvokeAsync(adapter, context);
        };

        for (int i = _filters.Count - 1; i >= 0; i--)
        {
            last = _filters[i](last);
        }

        last(adapter, context);
    }
}