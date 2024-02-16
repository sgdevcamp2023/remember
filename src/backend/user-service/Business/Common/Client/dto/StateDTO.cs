namespace user_service.common.dto;

public class UserIdsDTO
{
    public List<long> userIds { get; set; } = new List<long>();
}

public class ConnectsStatusDTO
{
    public Dictionary<string, string> connectionStates { get; set; } = new  Dictionary<string, string>();
}
