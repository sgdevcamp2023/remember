using user_service.common;
using user_service.common.exception;
using user_service.Controllers.dto.friend;
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

                public bool SendFriendAddRequest(long id, FriendDTO friend)
                {
                    long friendId = _friendRepository.GetFriendId(friend.FriendEmail);
                    if(friendId == 0)
                        throw new ServiceException(4007);

                    if(_friendRepository.CheckAlreadyFriend(id, friendId))
                        throw new ServiceException(4016);

                    if(!_friendRepository.SendFriendRequest(id, friendId))
                        throw new ServiceException(4017);
                    
                    return true;
                }

                public bool AcceptFriendAddRequest(long id, FriendDTO friend)
                {
                    return true;
                }

                public bool RefuseFriendAddRequest(long id, FriendDTO friend)
                {
                    return true;
                }

                public bool DeleteFriend(long id, FriendDTO friend)
                {
                    // return _friendRepository.DeleteFriend(id);

                    return true;
                }
            }
        }
    }
}