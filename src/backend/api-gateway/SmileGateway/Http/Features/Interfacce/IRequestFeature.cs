namespace ApiGatewayCore.Http.Feature;

public interface IRequestFeature : IBaseFeature
{
    public string Method { get; set; }
    public string Path { get; set; }
    public string Protocol { get; set; }
}
