using System.Buffers;
using SmileGatewayCore.Http.Context;
using Microsoft.Extensions.ObjectPool;

namespace SmileGatewayCore.Utils;

public class MemoryPool
{
    // Thread Safe Pool
    private ArrayPool<byte> _arrayPool = ArrayPool<byte>.Shared;
    // private ObjectPool<HttpRequest> _requestPool = new DefaultObjectPool<HttpRequest>(new DefaultPooledObjectPolicy<HttpRequest>());
    // private ObjectPool<HttpResponse> _responsePool = new DefaultObjectPool<HttpResponse>(new DefaultPooledObjectPolicy<HttpResponse>());

    public int _bufferSize { get; private set; }

    public MemoryPool(int bufferSize)
    {
        _bufferSize = bufferSize;
    }

    public ArraySegment<byte> RentBytes()
    {
        return _arrayPool.Rent(_bufferSize);
    }

    public void ReturnBytes(ArraySegment<byte> buffer)
    {
        _arrayPool.Return(buffer.Array!);
    }

    // public HttpRequest RentRequest()
    // {
    //     return _requestPool.Get();
    // }
    // public void ReturnRequest(HttpRequest request)
    // {
    //     _requestPool.Return(request);
    // }

    // public HttpResponse RentResponse()
    // {
    //     return _responsePool.Get();
    // }
    // public void ReturnResponse(HttpResponse response)
    // {
    //     _responsePool.Return(response);
    // }
}