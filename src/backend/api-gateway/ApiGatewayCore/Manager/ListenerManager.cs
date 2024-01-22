using ApiGatewayCore.Config;

namespace ApiGatewayCore.Manager;

public class ListenerManager
{
    private List<ListenerModel> _lisnterModel = null!;
    public void Init(List<ListenerModel> model)
    {
        _lisnterModel = model;
    }
}