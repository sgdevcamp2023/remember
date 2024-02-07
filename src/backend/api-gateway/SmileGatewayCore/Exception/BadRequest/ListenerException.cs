namespace SmileGatewayCore.Exception;

public class ListenerException : InternalException
{
    public ListenerException(int errorCode) : base(errorCode, "Listener Error")
    {
        
    }
}