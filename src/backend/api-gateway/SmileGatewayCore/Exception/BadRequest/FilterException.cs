namespace SmileGatewayCore.Exception;

public class FilterException : DefaultException
{
    public FilterException(int errorCode) : base(errorCode, "Filter Error")
    {
        
    }
}