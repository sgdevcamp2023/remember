using System.Buffers;
using System.Collections.Concurrent;

namespace ApiGatewayCore.Utils;

public class MemoryPool
{
    // ThreadSafe Queue
    // private ConcurrentQueue<ArraySegment<byte>> _queue = new ConcurrentQueue<ArraySegment<byte>>();

    private ArrayPool<byte> _arrayPool = ArrayPool<byte>.Shared;
    public int _bufferSize { get; private set; }

    public MemoryPool(int bufferSize)
    {
        _bufferSize = bufferSize;
    }

    public ArraySegment<byte> Dequeue()
    {
        // if(_queue.TryDequeue(out ArraySegment<byte> buffer))
        // {
        //     return buffer;
        // }
        // return new ArraySegment<byte>(new byte[_bufferSize], 0, _bufferSize);

        return _arrayPool.Rent(_bufferSize);
    }

    public void Enqueue(ArraySegment<byte> buffer)
    {
        // _queue.Enqueue(buffer);
        _arrayPool.Return(buffer.Array!);
    }

}