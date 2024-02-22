namespace user_service.common.exception;

public class CommonException : Exception
{
    public CommonException(string message) : base(message)
    {

    }

    public override string ToString()
    {
        string str = $"Common Exception : {Message}";
        return str;
    }
}
