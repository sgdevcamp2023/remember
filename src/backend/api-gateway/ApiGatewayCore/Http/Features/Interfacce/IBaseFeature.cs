using ApiGatewayCore.Http.Header;

namespace ApiGatewayCore.Http.Feature;

public interface IBaseFeature
{
    public string Method { get; set; }
    public string Path { get; set; }
    public string Protocol { get; set; }

    public HeaderDictionary Header { get; set; }
    public string? Body { get; set; }
}