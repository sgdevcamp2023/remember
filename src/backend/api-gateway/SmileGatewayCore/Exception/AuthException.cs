using SmileGatewayCore.Config;

namespace SmileGatewayCore.Exception;

public class AuthException : System.Exception
{
    public int ErrorCode { get; set; }
    public AuthException(int errorCode) : base("Auth Failed")
    {
        ErrorCode = errorCode;
    }

    public override string ToString()
    {
        return ErrorResponse.GetErrorInfo(ErrorCode);
    }
}