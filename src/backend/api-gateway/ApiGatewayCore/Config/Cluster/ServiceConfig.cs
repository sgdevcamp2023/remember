using System.Net.NetworkInformation;
namespace ApiGatewayCore.Config;

public class ServiceConfig
{
    public string Name { get; set; } = null!;
    public string Protocol { get; set; } = null!;
    public AddressConfig Address { get; set; } = null!;
    public string Prefix { get; set; } = null!;
    public CustomFilter? CustomFilter { get; set; }
    public string ConnectTimeout { get; set; } = null!;
    public bool Authorization { get; set; }
}