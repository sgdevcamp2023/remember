namespace SmileGatewayCore.Http.Context;

public class HttpContext
{
    public HttpContext()
    {
        Request = new HttpRequest();
        Response = new HttpResponse();
    }
    
    public HttpRequest Request { get; set; } = null!;
    public HttpResponse Response { get; set; } = null!;
}