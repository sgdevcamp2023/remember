using user_service.common;
using user_service.friend.repository;
using user_service.user.dto;

namespace user_service
{
    namespace friend
    {
        namespace service
        {
            public class FriendService
            {
                private IConfiguration _config;
                private IUserRepository _userRepository;
                private IFriendRepository _friendRepository;
                
                public FriendService(IConfiguration config, IUserRepository userRepository, IFriendRepository friendRepository)
                {
                    _config = config;
                    _userRepository = userRepository;
                    _friendRepository = friendRepository;
                }

                public List<UserDTO> GetFriendList(long id)
                {
                    return _friendRepository.GetFriendList(id);
                }

                public List<UserDTO>? GetRequestListByUserId(long id)
                {
                    return _friendRepository.ShowAllFriendRequestList(id);
                }

                public bool SendFriendAddRequest(long id, long friendId)
                {
                    return true;
                }

                public bool AcceptFriendAddRequest(long id, long friendId)
                {
                    return true;
                }

                public bool RefuseFriendAddRequest(long id, long friendId)
                {
                    return true;
                }

                public bool DeleteFriend(long id, long friendId)
                {
                    return _friendRepository.DeleteFriend(id);
                }
            }
        }
    }
}