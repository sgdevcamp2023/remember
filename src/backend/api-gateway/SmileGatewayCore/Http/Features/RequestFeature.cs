using SmileGatewayCore.Http.Header;

namespace SmileGatewayCore.Http.Feature;

public sealed class RequestFeature : IRequestFeature
{
    public RequestFeature()
    {
        Method = string.Empty;
        Path = string.Empty;
        QueryString = string.Empty;
        Protocol = string.Empty;

        TraceId = string.Empty;
        UserId = string.Empty;
        Header = new HeaderDictionary();
        ContentLength = 0;
        Body = string.Empty;
    }

    public string Method { get; set; } = null!;
    public string Path { get; set; } = null!;
    public string? QueryString { get; set; } = null!;
    public string Protocol { get; set; } = null!;

    public string TraceId { get; set; } = null!;
    public string UserId { get; set; } = null!;
    public HeaderDictionary Header { get; set; } = null!;
    public int ContentLength { get; set; }
    public string? Body { get; set; } = null!;
}