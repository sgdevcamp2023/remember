using SmileGatewayCore.Config;
using SmileGatewayCore.Instance.DownStream;

namespace SmileGatewayCore.Manager;

internal class ListenerManager
{
    public ClusterManager ClusterManager { get; set; } = null!;
    public List<Listener> Listeners { get; set; } = new List<Listener>();

    public void Init(List<ListenerConfig> models)
    {
        foreach (var model in models)
        {
            var listener = new Listener(ClusterManager, model);
            listener.Init();

            Listeners.Add(listener);
        }
    }

    public void Run()
    {
        
    }
}