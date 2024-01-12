namespace ServerCore
{
    public class Listener
    {
        public string Name { get; set; } = null!;
        public string Protocol { get; set; } = null!;
        public SocketAddress Address { get; set; } = null!;
        public RouteConfig RouteConfig { get; set; } = null!;
    }


}