namespace SmileGatewayCore.Exception;

public class NetworkException : InternalException
{
    public NetworkException(int errorCode) : base(errorCode, "Network Error")
    {
        
    }
}