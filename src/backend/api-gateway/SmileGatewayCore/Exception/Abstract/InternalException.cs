public abstract class InternalException : System.Exception
{
    public readonly int ErrorCode;
    public InternalException(int errorCode, string message) : base(message)
    {
        ErrorCode = errorCode;
    }
}