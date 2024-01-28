using SmileGatewayCore.Http.Header;

namespace SmileGatewayCore.Http.Feature;

public interface IBaseFeature
{
    public HeaderDictionary Header { get; set; }
    public int ContentLength { get; set; }
    public string? QueryString { get; set; }
    public string? Body { get; set; }
}