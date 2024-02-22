using System.Collections.Concurrent;
using SmileGatewayCore.Config;
using SmileGatewayCore.Instance.DownStream;

namespace SmileGatewayCore.Manager;

internal class ListenerManager
{
    public ClusterManager ClusterManager { get; set; } = null!;
    public ConcurrentDictionary<string, Listener> Listeners { get; set; } = new ConcurrentDictionary<string, Listener>();

    public void Init(List<ListenerConfig> configs)
    {
        foreach (var config in configs)
        {
            System.Console.WriteLine(config.Name + config.IsInside);
            Listeners.TryAdd(config.Name, CreateListener(config));
        }
    }

    public void Run()
    {
        foreach (var (name, listener) in Listeners)
        {
            listener.Run();
        }
    }

    public void Changed(List<ListenerConfig> configs)
    {
        foreach (ListenerConfig config in configs)
        {
            if (Listeners.TryGetValue(config.Name, out Listener? listener))
            {
                if (listener != null)
                    listener.Changed(config);
            }
            else
            {
                Listeners.TryAdd(config.Name, CreateListener(config));
            }
        }
    }

    private Listener CreateListener(ListenerConfig config)
    {
        Listener listener = new Listener(ClusterManager, config);
        listener.Init();

        return listener;
    }
}