using SmileGatewayCore.Config;

namespace SmileGatewayCore.Exception;

public class ConfigException : InternalException
{
    public ConfigException(int errorCode) : base(errorCode, "Config Exception")
    {
        
    }

    public override string ToString()
    {
        return ErrorResponse.GetErrorInfo(ErrorCode);
    }
}