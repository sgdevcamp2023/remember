using user_service.user.dto;

namespace user_service
{
    namespace friend
    {
        namespace repository
        {
            public interface IFriendRepository
            {
                public List<UserDTO> GetFriendList(long id);
                public List<UserDTO> ShowAllSendRequestList(long id);
                public List<UserDTO> ShowAllReceiveRequesttList(long id);
                public long GetFriendId(string email);
                public bool CheckAlreadyFriend(long id, long friendId);
                public bool SendFriendRequest(long id, long friendId);
                public bool AcceptFriendRequest(long id, long friendId);
                public bool RefuseFriendRequest(long id, long friendId);
                public bool DeleteFriend(long id);
            }   
        }
    }
}