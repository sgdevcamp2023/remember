namespace SmileGatewayCore.Exception;

public class ClusterException : DefaultException
{
    public ClusterException(int errorCode) : base(errorCode, "Cluster Error")
    {
        
        
    }
}