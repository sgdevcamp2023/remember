using ApiGatewayCore.Filter;
using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Instance;

public abstract class AbstractFilter
{
    protected List<Func<RequestDelegate, RequestDelegate>> _filters = new List<Func<RequestDelegate, RequestDelegate>>();
    public void UseFilter<T>()
    {
        UseFilter(typeof(T));
    }
    public void UseFilter(Type type)
    {
        Use(next => {
           return async context =>
            {
                var middleware = Activator.CreateInstance(type) as IFilterBase;

                if (middleware == null)
                {
                    throw new InvalidOperationException("middleware is null");
                }

                await middleware.InvokeAsync(context, next);
            };
        });
    }   
    public void Use(Func<RequestDelegate, RequestDelegate> filter)
    {
        _filters.Add(filter);
    }

    public void FilterStart(HttpContext context)
    {
        RequestDelegate next = (context) =>
        {
            return Task.CompletedTask;
        };

        for (int i = _filters.Count - 1; i >= 0; i--)
        {
            next = _filters[i](next);
        }

        next(context);
    }
}