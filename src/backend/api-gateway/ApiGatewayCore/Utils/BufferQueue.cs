namespace ApiGatewayCore.Utils;

public class BufferQueue
{
    ArraySegment<byte> _buffer;
    private int _readPos;
    private int _writePos;
    private Object _lock = new object();
    public BufferQueue(int bufferSize)
    {
        _buffer = new ArraySegment<byte>(new byte[bufferSize], 0, bufferSize);
        _readPos = 0;
        _writePos = 0;
    }

    public bool OnRead(int numOfBytes)
    {
        if (numOfBytes > DataSize)
            return false;

        _readPos += numOfBytes;
        return true;
    }

    public bool OnWrite(int numOfBytes)
    {
        if (numOfBytes > FreeSize)
            return false;

        _writePos += numOfBytes;
        return true;
    }

    public int DataSize => _writePos - _readPos;
    public int FreeSize => _buffer.Count - _writePos;

    public ArraySegment<byte> ReadSegment => new ArraySegment<byte>(_buffer.Array!, _buffer.Offset + _readPos, DataSize);

    public ArraySegment<byte> WriteSegment => new ArraySegment<byte>(_buffer.Array!, _buffer.Offset + _writePos, FreeSize);
}