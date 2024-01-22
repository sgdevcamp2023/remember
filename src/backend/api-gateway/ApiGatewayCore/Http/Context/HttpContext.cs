namespace ApiGatewayCore.Http.Context;

public class HttpContext
{
    public HttpRequest Request { get; set; } = null!;
    public HttpResponse Response { get; set; } = null!;
}