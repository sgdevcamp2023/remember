namespace Http.Features;

public sealed class RequestFeature : IRequestFeature
{
    public string Method { get; set; } = null!;
    public string Path { get; set; } = null!;
    public string Protocol { get; set; } = null!;

    public string Header { get; set; } = null!;
    public string Body { get; set; } = null!;
}