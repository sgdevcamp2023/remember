using ApiGatewayCore.Filter.Listner;

namespace ApiGatewayCore.Instance.DownStream;

public class ListenerFilterChains : FilterInstnace
{
    public override void Init()
    {
        // UseFilter<ExceptionFilter>();
        // UseFilter<ServiceFilter>();
        // UseFilter<ProtocolCheckFilter>();
        UseFilter<AuthorizationFilter>();
        // UseFilter<ConnectionFilter>();
    }
}