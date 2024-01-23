namespace ApiGatewayCore.Config;
public class ListenerModel
{
    public string Name { get; set; } = null!;
    public string Protocol { get; set; } = null!;
    public SocketAddressModel Address { get; set; } = null!;
    public RouteConfig RouteConfig { get; set; } = null!;
    public CustomFilter? CustomFilter { get; set; }
    public Authorization? Authorization { get; set; }
    public int ThreadCount { get; set; }
}