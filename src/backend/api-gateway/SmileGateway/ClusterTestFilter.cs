using SmileGatewayCore.Filter.Cluster;
using SmileGatewayCore.Http.Context;

public class ClusterTestFilter : ClusterFilter
{
    public override void Worked(HttpContext context)
    {
        Console.WriteLine("ClusterTestFilter Worked");
    }

    public override void Working(HttpContext context)
    {
        Console.WriteLine("ClusterTestFilter Working");
    }
}