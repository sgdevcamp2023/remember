using user_service.Controllers.dto.friend;
using user_service.user.dto;

namespace user_service.friend.service;

public interface IFriendService
{
    Task<List<FriendInfoDTO>> GetFriendList(long id);
    List<FriendInfoDTO> GetSendRequestList(long id);
    List<FriendInfoDTO> GetReceiveRequestList(long id);
    void SendFriendAddRequest(FriendDTO friend);
    void CancleFriendAddRequest(FriendDTO friend);
    Task AcceptFriendAddRequestAsync(FriendDTO friend);
    void RefuseFriendAddRequest(FriendDTO friend);
    bool DeleteFriend(FriendDTO friend);
}