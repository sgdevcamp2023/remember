namespace user_service.common.exception;
public class ClientException : CommonException
{
    public int ErrorCode { get; set; }
    public ClientException(int errorCode) : base("Client Exception")
    {
        ErrorCode = errorCode;
    }

    public override string ToString()
    {
        string str = $"Exception {ErrorCode} : {Message}";
        return str;
    }
}