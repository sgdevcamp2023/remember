using ApiGatewayCore.Config;

namespace ApiGatewayCore.Filter.Config;

// 필터 처리에 필요한 설정을 담는 클래스
public class FilterConfig
{
    public JwtValidator? JwtValidator { get; set; }
    // public Dictionary<string, 
}