namespace SmileGatewayCore.Config;
public class Authorization
{
    public string Type { get; set; } = null!;
    public string LoginPath { get; set; } = null!;
    public string LogoutPath { get; set; } = null!;
    public JwtValidator JwtValidator { get; set; } = null!;
}