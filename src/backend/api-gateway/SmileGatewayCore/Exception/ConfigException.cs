using SmileGatewayCore.Config;

namespace SmileGatewayCore.Exception;

public class ConfigException : System.Exception
{
    public int ErrorCode { get; set; }
    public ConfigException(int errorCode) : base("Auth Failed")
    {
        ErrorCode = errorCode;
    }

    public override string ToString()
    {
        return ErrorResponse.GetErrorInfo(ErrorCode);
    }
}