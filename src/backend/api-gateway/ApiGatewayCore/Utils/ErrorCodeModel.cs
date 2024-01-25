namespace ApiGatewayCore.Utils;

public class ErrorCodeModel
{
    public int code { get; set; }
    public string message { get; set; } = null!;
    public string description { get; set; } = null!;
}
