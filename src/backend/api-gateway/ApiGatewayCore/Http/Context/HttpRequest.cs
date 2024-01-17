

using ApiGatewayCore.Http.Feature;

namespace ApiGatewayCore.Http.Context;
public class HttpRequest
{
    IRequestFeature _requestFeature;

    public HttpRequest()
    {
        _requestFeature = new RequestFeature();
    }
}