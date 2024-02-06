namespace SmileGatewayCore.Config;
public class ClusterConfig
{
    public string Name { get; set; } = null!;
    public string Protocol { get; set; } = null!;
    // 스케일 아웃 생각
    public List<AddressConfig> Address { get; set; } = null!;
    public string Prefix { get; set; } = null!;
    public List<CustomFilterConfig>? CustomFilters { get; set; }
    public string ConnectTimeout { get; set; } = null!;
    public bool Authorization { get; set; }
}