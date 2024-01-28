using SmileGatewayCore.Filter.Listner;

namespace SmileGatewayCore.Instance.DownStream;

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