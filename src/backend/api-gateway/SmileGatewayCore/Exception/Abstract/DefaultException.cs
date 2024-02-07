namespace SmileGatewayCore.Exception;

public abstract class DefaultException : System.Exception
{
    public readonly int ErrorCode;
    public DefaultException(int errorCode, string message) : base(message)
    {
        ErrorCode = errorCode;
    }
}
