namespace SmileGatewayCore.Config;
public class ListenerConfig
{
    public string Name { get; set; } = null!;
    public string Protocol { get; set; } = null!;
    public AddressConfig Address { get; set; } = null!;
    public RouteConfig RouteConfig { get; set; } = null!;
    public List<CustomFilterConfig>? CustomFilters { get; set; }
    public Authorization? Authorization { get; set; }
    public List<string> DisallowHeaders { get; set; } = null!;
    public bool IsInside { get; set; }
    public int ThreadCount { get; set; }
    public int? RequestTimeout { get; set; }
}