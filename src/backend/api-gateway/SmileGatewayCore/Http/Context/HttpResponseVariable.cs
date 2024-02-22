using SmileGatewayCore.Http.Feature;
using SmileGatewayCore.Http.Features;
using SmileGatewayCore.Http.Header;
namespace SmileGatewayCore.Http.Context;

public partial class HttpResponse
{
    IResponseFeature _responseFeatrue;
    IResponseCookie? _responseCookie = null;

    public bool IsChucked { get; set; } = false;
    public string Protocol
    {
        get => _responseFeatrue.Protocol;
        set => _responseFeatrue.Protocol = value;
    }

    public int StatusCode
    {
        get => _responseFeatrue.StatusCode;
        set => _responseFeatrue.StatusCode = value;
    }

    public string StatusMessage
    {
        get => _responseFeatrue.StatusMessage;
        set => _responseFeatrue.StatusMessage = value;
    }
    public string TraceId
    {
        get => _responseFeatrue.TraceId;
        set => _responseFeatrue.TraceId = value;
    }
    public string UserId
    {
        get => _responseFeatrue.UserId;
        set => _responseFeatrue.UserId = value;
    }
    public HeaderDictionary Header
    {
        get => _responseFeatrue.Header;
        set => _responseFeatrue.Header = value;
    }
    public string? Body
    {
        get => _responseFeatrue.Body;
        set => _responseFeatrue.Body = value;
    }
    public int ContentLength
    {
        get => _responseFeatrue.ContentLength;
        set => _responseFeatrue.ContentLength = value;
    }
    public IResponseCookie? Cookie
    {
        get => _responseCookie;
        set => _responseCookie = value;
    }
    public List<string> Varys
    {
        get => _responseFeatrue.Varys;
        set => _responseFeatrue.Varys = value;
    }
}