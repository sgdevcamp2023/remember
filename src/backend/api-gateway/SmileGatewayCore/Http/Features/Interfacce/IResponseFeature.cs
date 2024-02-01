namespace SmileGatewayCore.Http.Feature;

public interface IResponseFeature : IBaseFeature
{
    public string Protocol { get; set; }
    public int StatusCode { get; set; }
    public string StatusMessage { get; set; }
    public List<string> Varys { get; set; }
}