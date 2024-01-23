namespace ApiGatewayCore.Http.Context;

public class HttpContext
{
    public HttpContext(HttpRequest request, HttpResponse response)
    {
        Request = request;
        Response = response;
    }
    public HttpRequest Request { get; set; } = null!;
    public HttpResponse Response { get; set; } = null!;
}