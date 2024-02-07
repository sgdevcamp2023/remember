namespace SmileGatewayCore.Exception;

public class NetworkException : DefaultException
{
    public NetworkException(int errorCode) : base(errorCode, "Network Error")
    {
        
    }
}