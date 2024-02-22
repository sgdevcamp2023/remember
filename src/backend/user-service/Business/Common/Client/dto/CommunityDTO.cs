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

public class CommunityResponseDTO
{
    public int ResultCode { get; set; }
    public string resultMessage { get; set; } = null!;
    public string ResultData { get; set; } = null!;
}

public class CommunityBaseException
{
    public int Code { get; set; }
    public string Exception { get; set; } = null!;
    public string Message { get; set; } = null!;
}

public class CommunityRoomCreateDTO
{
    public string name { get; set; } = null!;
    public List<long> members { get; set; } = null!;
    public string profile { get; set; } = null!;
}