using Newtonsoft.Json;
using SmileGatewayCore.Config;
using SmileGatewayCore.Utils;

namespace SmileGatewayCore.Exception;

public class ConfigException : InternalException
{
    public ConfigException(int errorCode) : base(errorCode, "Config Exception")
    {
        
    }

    public override string ToString()
    {
        string info = ErrorResponse.Instance.GetErrorInfo(ErrorCode);
        ErrorCodeModel? errorCode = JsonConvert.DeserializeObject<ErrorCodeModel>(info);
        if(errorCode != null)
        {
            return $"ErrorCode: {ErrorCode}, Message: {errorCode.description}";
        }
        else
        {
            return info;
        }

    }
}