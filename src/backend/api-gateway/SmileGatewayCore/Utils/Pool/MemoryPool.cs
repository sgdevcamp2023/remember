using System.Buffers;

namespace SmileGatewayCore.Utils;

public class MemoryPool
{
    // Thread Safe Pool
    private ArrayPool<byte> _arrayPool = ArrayPool<byte>.Shared;

    public ArraySegment<byte> RentDefaultBytes()
    {
        return _arrayPool.Rent(Buffers.bufferSize);
    }

    public void ReturnBytes(ArraySegment<byte> buffer)
    {
        _arrayPool.Return(buffer.Array!);
    }

    public ArraySegment<byte> RentMultipartBytes()
    {
        return _arrayPool.Rent(Buffers.multipartSize - Buffers.bufferSize);
    }
}