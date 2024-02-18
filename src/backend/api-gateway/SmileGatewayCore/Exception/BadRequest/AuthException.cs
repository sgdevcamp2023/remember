using SmileGatewayCore.Config;

namespace SmileGatewayCore.Exception;

public class AuthException : DefaultException
{
    public AuthException(int errorCode) : base(errorCode, "Auth Failed")
    {
        
    }

    public override string ToString()
    {
        return ErrorResponse.Instance.GetErrorInfo(ErrorCode);
    }
}