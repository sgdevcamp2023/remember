namespace ApiGatewayCore.Config;
public class JwtValidator
{
    public string Path { get; set; } = null!;
    public string ValidAudience { get; set; } = null!;
    public string ValidIssuer { get; set; } = null!;
    public string SecretKey { get; set; } = null!;
    public int AccessTokenValidityInSecond { get; set; }
    public int RefreshTokenValidityInSecond { get; set; }
}