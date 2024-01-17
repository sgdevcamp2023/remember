namespace ApiGatewayCore.Http.Feature;

public interface IBaseFeature
{
    public string Method { get; set; }
    public string Path { get; set; }
    public string Protocol { get; set; }

    public string Header { get; set; }
    public string Body { get; set; }
}