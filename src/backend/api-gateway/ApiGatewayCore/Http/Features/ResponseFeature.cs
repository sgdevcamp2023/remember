using ApiGatewayCore.Http.Header;

namespace ApiGatewayCore.Http.Feature;

public sealed class ResponseFeature : IResponseFeature
{
    public ResponseFeature()
    {
        StatusMessage = string.Empty;
        Protocol = string.Empty;

        Header = new HeaderDictionary();
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