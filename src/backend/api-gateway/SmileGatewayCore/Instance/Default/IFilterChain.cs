using SmileGatewayCore.Http.Context;

namespace SmileGatewayCore.Instance;

public interface IFilterChain<d, data> where d : Delegate where data : class
{
    public void UseFilter(string filterName);
    public void UseFilter<T>();
    public void UseFilter(Type type);
    public void Use(Func<d, d> filter);
    public Task FilterStartAsync(data adapter, HttpContext context);
}