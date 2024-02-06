namespace SmileGatewayCore.Exception;

public class ListenerException : DefaultException
{
    public ListenerException(int errorCode) : base(errorCode, "Listener Error")
    {
        
    }
}