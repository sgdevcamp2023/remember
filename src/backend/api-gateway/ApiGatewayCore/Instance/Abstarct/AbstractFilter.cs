using ApiGatewayCore.Filter;

namespace ApiGatewayCore.Instance;

public abstract class AbstractFilter
{
    protected List<Func<RequestDelegate, RequestDelegate>> _filters = new List<Func<RequestDelegate, RequestDelegate>>();
    public void UseMiddleware<T>()
    {
        UseMiddleware(typeof(T));
    }
    public void UseMiddleware(Type type)
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
}