
using System.Net.Sockets;
using ApiGatewayCore.Config;
using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Instance;

internal class Cluster : DefaultInstance
{
    public readonly ClusterConfig config;
    public Cluster(ClusterConfig config)
    {
        this.config = config;
    }

    public override void Init()
    {
        throw new NotImplementedException();
    }

    public override Task Run()
    {
        throw new NotImplementedException();
    }

    public void Start(HttpContext context)
    {

    }

    protected override void OnReceive(Socket socket, ArraySegment<byte> buffer)
    {
        
    }

    protected override void OnSend(Socket socket, ArraySegment<byte> buffer)
    {
        
    }
}