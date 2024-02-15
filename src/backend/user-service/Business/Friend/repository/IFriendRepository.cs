using user_service.Controllers.dto.friend;

namespace user_service
{
    namespace friend
    {
        namespace repository
        {
            public interface IFriendRepository
            {
                public List<FriendInfoDTO> GetFriendList(long id);
                public List<FriendInfoDTO> ShowAllSendRequestList(long id);
                public List<FriendInfoDTO> ShowAllReceiveRequesttList(long id);
                public long GetFriendId(string email);
                public bool CheckAlreadyFriend(long id, long friendId);
                public bool SendFriendRequest(long id, long friendId);
                public bool CancleFriendRequest(long id, long friendId);
                public bool AcceptFriendRequest(long id, long friendId);
                public bool RefuseFriendRequest(long id, long friendId);
                public bool DeleteFriend(long id, long friendId);
            }   
        }
    }
}