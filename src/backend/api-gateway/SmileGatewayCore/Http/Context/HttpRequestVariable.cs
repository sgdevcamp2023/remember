using SmileGatewayCore.Http.Feature;
using SmileGatewayCore.Http.Header;

namespace SmileGatewayCore.Http.Context;

public partial class HttpRequest
{
    private IRequestFeature _requestFeature;
    private IRequestCookie? _requestCookie;
    public bool IsMultipart { get; set; } = false;
    private string _boundary = null!;
    private byte[] _endBoundary = null!;


    public string Method
    {
        get => _requestFeature.Method;
        set => _requestFeature.Method = value;
    }

    public string Path
    {
        get => _requestFeature.Path;
        set => _requestFeature.Path = value;
    }

    public string Protocol
    {
        get => _requestFeature.Protocol;
        set => _requestFeature.Protocol = value;
    }
    public string TraceId
    {
        get => _requestFeature.TraceId;
        set => _requestFeature.TraceId = value;
    }
    public string UserId
    {
        get => _requestFeature.UserId;
        set => _requestFeature.UserId = value;
    }
    public HeaderDictionary Header
    {
        get => _requestFeature.Header;
        set => _requestFeature.Header = value;
    }

    public string? Body
    {
        get => _requestFeature.Body;
        set => _requestFeature.Body = value;
    }

    public string? QueryString
    {
        get => _requestFeature.QueryString;
        set => _requestFeature.QueryString = value;
    }

    public int ContentLength
    {
        get => _requestFeature.ContentLength;
        set => _requestFeature.ContentLength = value;
    }

    public IRequestCookie? Cookie
    {
        get => _requestCookie;
        set => _requestCookie = value;
    }

    public List<byte> MultipartBody
    {
        get => _requestFeature.MultipartBody;
        set => _requestFeature.MultipartBody = value;
    }
}