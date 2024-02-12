using user_service.Controllers.dto.friend;
using user_service.user.dto;

namespace user_service.friend.service;

public interface IFriendService
{
    List<UserDTO> GetFriendList(long id);
    List<UserDTO> GetSendRequestList(long id);
    List<UserDTO> GetReceiveRequestList(long id);
    void SendFriendAddRequest(FriendDTO friend);
    void CancleFriendAddRequest(FriendDTO friend);
    void AcceptFriendAddRequest(FriendDTO friend);
    void RefuseFriendAddRequest(FriendDTO friend);
    bool DeleteFriend(FriendDTO friend);
}