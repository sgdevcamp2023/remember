namespace ApiGatewayCore.Config;
public class JwtValidator
{
    public string ClusterName { get; set; } = null!;
    public string Url { get; set; } = null!;
    public string? HeaderName { get; set; } = "Authorization";
    public string? Prefix { get; set; } = "Bearer";
    public List<string> SuccessStatus { get; set; } = null!;
}