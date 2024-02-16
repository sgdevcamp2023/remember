namespace user_service.common.dto;

public class CommunityUserDTO
{
    public long userId { get; set; }
    public string email { get; set; } = null!;
    public string name { get; set; } = null!;
    public string profile { get; set; } = null!;
}

public class CommunityNameDTO
{
    public long userId { get; set; }
    public string nickname { get; set; } = null!;
}

public class CommunityProfileDTO
{
    public long userId { get; set; }
    public string profile { get; set; } = null!;
}

public class CommunityResponseDTO<T>
{
    public int ResultCode { get; set; }
    public string ResultMessage { get; set; } = null!;
    public T? ResultData { get; set; }
}

public class CommunityBaseException
{
    public int Code { get; set; }
    public string Exception { get; set; } = null!;
    public string Message { get; set; } = null!;
}