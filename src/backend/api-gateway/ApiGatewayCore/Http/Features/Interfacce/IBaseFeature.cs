using ApiGatewayCore.Http.Header;

namespace ApiGatewayCore.Http.Feature;

public interface IBaseFeature
{
    public HeaderDictionary Header { get; set; }
    public string? QueryString { get; set; }
    public string? Body { get; set; }
}