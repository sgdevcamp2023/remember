using Http.Features;

namespace Http.Context;

public sealed class RequestContext
{
    public IRequestFeature requestFeature;

    public RequestContext()
    {
        requestFeature = new RequestFeature();
    }
}