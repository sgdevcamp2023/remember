using SmileGatewayCore.Config;

namespace SmileGatewayCore.Exception;

public class ConfigException : DefaultException
{
    public ConfigException(int errorCode) : base(errorCode, "Config Exception")
    {
        
    }

    public override string ToString()
    {
        return ErrorResponse.GetErrorInfo(ErrorCode);
    }
}