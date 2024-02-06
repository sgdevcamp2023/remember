using SmileGatewayCore.Config;
using SmileGatewayCore.Instance.Upstream;
using SmileGatewayCore.Utils.Logger;

namespace SmileGatewayCore.Instance;

public class Adapter
{
    internal Authorization? Authorization { get; set; }
    internal List<string>? DisallowHeaders { get; set; }
    internal AddressConfig Address { get; set; } = null!;
    internal Cluster Cluster { get; set; } = null!;
    internal string UserIP { get; set; } = null!;
    
    internal Adapter(ListenerConfig config, Cluster cluster, string userIP)
    {
        Authorization = config.Authorization;
        Address = config.Address;
        Cluster = cluster;
        UserIP = userIP;
    }
}