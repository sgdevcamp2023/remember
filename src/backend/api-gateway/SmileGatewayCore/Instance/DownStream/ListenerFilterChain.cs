using SmileGatewayCore.Filter.Listner;
using SmileGatewayCore.Http.Context;

namespace SmileGatewayCore.Instance.DownStream;

public class ListenerFilterChains : IListenerFilterChain
{
    protected List<Func<ListenerDelegate, ListenerDelegate>> _filters = new List<Func<ListenerDelegate, ListenerDelegate>>();

    public void Init()
    {

    }

    public void SetLastFilter()
    {

    }

    public void UseFilter(string filterName)
    {
        Type? type = Type.GetType(filterName + ", SmileGateway");
        if(type == null)
            throw new Exception();

        if(type.IsSubclassOf(typeof(IListenerFilterBase)) == false)
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

        await last(adapter, context);
    }
}