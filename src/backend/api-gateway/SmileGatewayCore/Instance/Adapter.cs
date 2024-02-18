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
    internal JwtValidator JwtValidator { get => Authorization!.jwtValidator; }
    internal Adapter(Authorization? auth, List<string> disallowHeaders, AddressConfig address, Cluster cluster, string userIP)
    {
        Authorization = auth;
        DisallowHeaders = disallowHeaders;
        Address = address;
        Cluster = cluster;
        UserIP = userIP;
    }

    internal string ListenerAddress {get => Address.Address + ":" + Address.Port;}

}