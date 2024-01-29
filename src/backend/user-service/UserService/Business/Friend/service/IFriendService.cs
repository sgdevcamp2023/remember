using user_service.Controllers.dto.friend;
using user_service.user.dto;

namespace user_service.friend.service;

public interface IFriendService
{
    List<UserDTO> GetFriendList(long id);
    List<UserDTO> GetSendRequestList(long id);
    List<UserDTO> GetReceiveRequestList(long id);
    void SendFriendAddRequest(long id, FriendDTO friend);
    void CancleFriendAddRequest(long id, FriendDTO friend);
    void AcceptFriendAddRequest(long id, FriendDTO friend);
    void RefuseFriendAddRequest(long id, FriendDTO friend);
    bool DeleteFriend(long id, FriendDTO friend);
}