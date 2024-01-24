using ApiGatewayCore.Config;
using ApiGatewayCore.Instance.Listener;

namespace ApiGatewayCore.Manager;

public class ListenerManager
{
    // private List<ListenerModel> _lisnterModel = null!;
    private List<Listener> _listeners = new List<Listener>();
    public void Init(List<ListenerConfig> models)
    {
        foreach(var model in models)
        {
            var listener = new Listener(model);
            listener.Init();

            _listeners.Add(listener);
        }
    }
}