using YamlDotNet.RepresentationModel;

namespace ServerCore
{
    public class Root
    {
        public List<Listener> Listeners { get; set; } = null!;
        public List<Cluster> Clusters { get; set; } = null!;
    }
}