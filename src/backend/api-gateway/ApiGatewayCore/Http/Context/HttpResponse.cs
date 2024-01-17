using ApiGatewayCore.Http.Feature;

namespace ApiGatewayCore.Http.Context;


public class HttpResponse
{
    IResponseFeature _responseFeatrue;

    public HttpResponse()
    {
        _responseFeatrue = new ResponseFeature();
    }
}