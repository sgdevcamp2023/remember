namespace SmileGatewayCore.Filter.Internal;

public class JwtClaimsModel
{
    public JwtClaimsModel(string Name)
    {
        this.Name = Name;
    }

    public string Name { get; set; } = null!;
}