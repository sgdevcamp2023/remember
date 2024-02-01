using SmileGatewayCore.Http.Header;

namespace SmileGatewayCore.Http.Feature;

public sealed class ResponseFeature : IResponseFeature
{
    public ResponseFeature()
    {
        Protocol = string.Empty;
        StatusCode = 0;
        StatusMessage = string.Empty;
        QueryString = string.Empty;
        Header = new HeaderDictionary();
        ContentLength = 0;
        Body = string.Empty;
    }

    public string Protocol { get; set; } = null!;
    public int StatusCode { get; set; }
    public string StatusMessage { get; set; } = null!;
    public string? QueryString { get; set; }
    public HeaderDictionary Header { get; set; } = null!;
    public int ContentLength { get; set; }
    public string? Body { get; set; }
}