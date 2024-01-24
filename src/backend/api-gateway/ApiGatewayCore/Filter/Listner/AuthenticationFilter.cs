using ApiGatewayCore.Http.Context;
using YamlDotNet.Core.Tokens;

namespace ApiGatewayCore.Filter.Listner;

public class AuthenticationFilter : DefaultFilter
{
    public override void Working(HttpContext context)
    {
        if(context.Request.Header.TryGetValue("Authorization", out string? token))
        {
            if(token == null)
                throw new Exception();

            if(!ValidationToken(token))
                throw new Exception();
        }
    }
    public override void Worked(HttpContext context)
    {
        
    }

    private bool ValidationToken(string token)
    {
        return true;
    }
}