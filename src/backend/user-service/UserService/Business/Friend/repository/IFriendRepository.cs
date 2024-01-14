using user_service.user.dto;

namespace user_service
{
    namespace friend
    {
        namespace repository
        {
            public interface IFriendRepository
            {
                public List<UserDTO>? GetFriendList(long id);
                public List<UserDTO>? ShowAllFriendRequestList(long id);
                public bool SendFriendRequest(long id, string email);
                public bool AcceptFriendRequest(long id, string email);
                public bool RefuseFriendRequest(long id, string email);
                public bool DeleteFriend(long id, string email);
            }   
        }
    }
}