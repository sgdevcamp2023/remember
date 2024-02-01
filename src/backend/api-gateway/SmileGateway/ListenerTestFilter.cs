using SmileGatewayCore.Filter.Listner;
using SmileGatewayCore.Http.Context;

public class ListenerTestFilter : CustomFilter
{
    public override void Worked(HttpContext context)
    {
        // // Console.WriteLine("ListenerTestFilter Worked");
    }

    public override void Working(HttpContext context)
    {
        // Console.WriteLine("ListenerTestFilter Working");
    }
}