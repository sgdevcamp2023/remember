namespace SmileGatewayCore.Config;
public class ClusterConfig
{
    public string Name { get; set; } = null!;
    public string Protocol { get; set; } = null!;
    // 스케일 아웃 생각
    public List<AddressConfig> Address { get; set; } = null!;
    public string Prefix { get; set; } = null!;
    public List<CustomFilterConfig>? CustomFilters { get; set; }
    public bool Authorization { get; set; }
    public int? ConnectTimeout { get; set; }
    public int? RequestTimeout { get; set; }
}