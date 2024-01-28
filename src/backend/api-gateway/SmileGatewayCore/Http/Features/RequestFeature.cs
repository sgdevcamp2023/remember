using SmileGatewayCore.Http.Header;

namespace SmileGatewayCore.Http.Feature;

public sealed class RequestFeature : IRequestFeature
{
    public RequestFeature()
    {
        Method = string.Empty;
        Path = string.Empty;
        Protocol = string.Empty;

        Header = new HeaderDictionary();
        Body = string.Empty;
    }

    public string Method { get; set; } = null!;
    public string Path { get; set; } = null!;
    public string Protocol { get; set; } = null!;

    public int ContentLength { get; set; }
    public HeaderDictionary Header { get; set; } = null!;
    public string? Body { get; set; } = null!;
    public string? QueryString { get; set; } = null!;
}