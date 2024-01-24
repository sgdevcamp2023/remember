namespace ApiGatewayCore.Utils;

public class MemoryPool
{
    private Queue<ArraySegment<byte>> _queue = new Queue<ArraySegment<byte>>();
    public int _bufferSize { get; private set; }
    private object _lock;
    
    public MemoryPool(int bufferSize)
    {
        _bufferSize = bufferSize;
        _lock = new object();
    }

    public ArraySegment<byte> Dequeue()
    {
        lock(_lock)
        {
            if(_queue.Count == 0)
            {
                return new ArraySegment<byte>(new byte[_bufferSize], 0, _bufferSize);
            }
            return _queue.Dequeue();
        }
    }

    public void Enqueue(ArraySegment<byte> buffer)
    {
        if(buffer.Count != _bufferSize)
            throw new Exception();
        
        lock(_lock)
        {
            _queue.Enqueue(buffer);
        }
    }
    
}