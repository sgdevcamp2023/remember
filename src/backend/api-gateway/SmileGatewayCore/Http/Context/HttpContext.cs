namespace SmileGatewayCore.Http.Context;

public class HttpContext
{
    public HttpContext(HttpRequest request, HttpResponse response)
    {
        Request = request;
        Response = response;
    }
    public HttpContext (string? request = null, string? response = null)
    {
        Request = (request == null ? new HttpRequest() : new HttpRequest(request));
        Response = (response == null ? new HttpResponse() : new HttpResponse(response));
    }
    
    public HttpRequest Request { get; set; } = null!;
    public HttpResponse Response { get; set; } = null!;
}