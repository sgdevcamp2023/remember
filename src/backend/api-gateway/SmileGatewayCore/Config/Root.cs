namespace SmileGatewayCore.Config;
public class Root
{
    public string LogPath { get; set; } = null!;    
    public List<ListenerConfig> Listeners { get; set; } = null!;
    public List<ClusterConfig> Clusters { get; set; } = null!;
}
