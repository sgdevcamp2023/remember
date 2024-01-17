using System.Net.NetworkInformation;

namespace ServerCore
{
    public class Service
    {
        public string Name { get; set; } = null!;
        public string Protocol { get; set; } = null!;
        public SocketAddress Address { get; set; } = null!;
        public string Prefix { get; set; } = null!;
        public CustomFilter? CustomFilter { get; set; }
        public string ConnectTimeout { get; set; } = null!;
        public bool Authorization { get; set; }
    }
}