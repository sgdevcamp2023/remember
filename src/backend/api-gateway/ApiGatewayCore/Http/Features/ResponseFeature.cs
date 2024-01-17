namespace ApiGatewayCore.Http.Feature;

public sealed class ResponseFeature : IResponseFeature
{

    public ResponseFeature()
    {
        Method = string.Empty;
        Path = string.Empty;
        Protocol = string.Empty;

        Header = string.Empty;
        Body = string.Empty;
    }
    public string Method { get; set; } = null!;
    public string Path { get; set; } = null!;
    public string Protocol { get; set; } = null!;

    public string Header { get; set; } = null!;
    public string Body { get; set; } = null!;
}