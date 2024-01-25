namespace ApiGatewayCore.Config;
public class ListenerConfig
{
    public string Name { get; set; } = null!;
    public string Protocol { get; set; } = null!;
    public AddressConfig Address { get; set; } = null!;
    public RouteConfig RouteConfig { get; set; } = null!;
    public CustomFilter? CustomFilter { get; set; }
    public Authorization Authorization { get; set; } = null!;
    public List<string> DisallowHeaders { get; set; } = null!;
    public int ThreadCount { get; set; }
}