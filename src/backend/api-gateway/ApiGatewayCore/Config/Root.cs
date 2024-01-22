using YamlDotNet.RepresentationModel;

namespace ApiGatewayCore.Config;
public class Root
{
    public List<ListenerModel> Listeners { get; set; } = null!;
    public List<ClusterModel> Clusters { get; set; } = null!;
}
