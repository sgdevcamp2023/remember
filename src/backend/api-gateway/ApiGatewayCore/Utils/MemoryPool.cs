using System.Buffers;

namespace ApiGatewayCore.Utils;

public class MemoryPool
{
    // Thread Safe Pool
    private ArrayPool<byte> _arrayPool = ArrayPool<byte>.Shared;
    public int _bufferSize { get; private set; }

    public MemoryPool(int bufferSize)
    {
        _bufferSize = bufferSize;
    }

    public ArraySegment<byte> Dequeue()
    {
        return _arrayPool.Rent(_bufferSize);
    }

    public void Enqueue(ArraySegment<byte> buffer)
    {
        _arrayPool.Return(buffer.Array!);
    }

}