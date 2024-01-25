using System.Collections.Concurrent;

namespace ApiGatewayCore.Utils;

public class MemoryPool
{
    private ConcurrentQueue<ArraySegment<byte>> _queue = new ConcurrentQueue<ArraySegment<byte>>();
    public int _bufferSize { get; private set; }
    private object _lock;

    public MemoryPool(int bufferSize)
    {
        _bufferSize = bufferSize;
        _lock = new object();
    }

    public ArraySegment<byte> Dequeue()
    {
        if(_queue.TryDequeue(out ArraySegment<byte> buffer))
        {
            return buffer;
        }

        return new ArraySegment<byte>(new byte[_bufferSize], 0, _bufferSize);
    }

    public void Enqueue(ArraySegment<byte> buffer)
    {
        _queue.Enqueue(buffer);
    }

}