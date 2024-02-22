using System.Data;
using user_service.user.dto;

namespace user_service.Controllers.dto.friend;

public class FriendDTO
{
    public long MyId { get; set; }
    public string FriendEmail { get; set; } = null!;
}

public class FriendInfoDTO : UserDTO
{
    public bool IsOnline { get; set; }
}