using Castle.DynamicProxy;
using user_service.common;
using user_service.common.exception;
using user_service.Controllers.dto.friend;
using user_service.friend.repository;
using user_service.intercepter;
using user_service.user.dto;

namespace user_service
{
    namespace friend
    {
        namespace service
        {
            public class FriendService : IFriendService
            {
                private IConfiguration _config;
                private IUserRepository _userRepository;
                private IFriendRepository _friendRepository;

                public FriendService(IConfiguration config,
                                    IUserRepository userRepository,
                                    IFriendRepository friendRepository,
                                    LogInterceptor interceptor)
                {
                    var generator = new ProxyGenerator();
                    _userRepository = generator.CreateInterfaceProxyWithTargetInterface<IUserRepository>(userRepository, interceptor);
                    _config = config;
                    _friendRepository = friendRepository;
                }

                public List<UserDTO> GetFriendList(long id)
                {
                    return _friendRepository.GetFriendList(id);
                }

                public List<UserDTO> GetSendRequestList(long id)
                {
                    return _friendRepository.ShowAllSendRequestList(id);
                }

                public List<UserDTO> GetReceiveRequestList(long id)
                {
                    return _friendRepository.ShowAllReceiveRequesttList(id);
                }

                public void SendFriendAddRequest(FriendDTO friend)
                {
                    long id = friend.MyId;
                    long friendId = GetFriendId(friend.FriendEmail);

                    if(_friendRepository.CheckAlreadyFriend(id, friendId))
                        throw new ServiceException(4016);

                    if(!_friendRepository.SendFriendRequest(id, friendId))
                        throw new ServiceException(4017); 
                }

                public void CancleFriendAddRequest(FriendDTO friend)
                {
                    long id = friend.MyId;
                    long friendId = GetFriendId(friend.FriendEmail);

                    if(!_friendRepository.CancleFriendRequest(id, friendId))
                        throw new ServiceException(4020);
                }

                public void AcceptFriendAddRequest(FriendDTO friend)
                {
                    long id = friend.MyId;
                    long friendId = GetFriendId(friend.FriendEmail);
                    
                    if(!_friendRepository.AcceptFriendRequest(id, friendId))
                        throw new ServiceException(4018);
                }

                public void RefuseFriendAddRequest(FriendDTO friend)
                {
                    long id = friend.MyId;
                    long friendId = GetFriendId(friend.FriendEmail);

                    if(!_friendRepository.RefuseFriendRequest(id, friendId))
                        throw new ServiceException(4019);
                }

                public bool DeleteFriend(FriendDTO friend)
                {
                    long id = friend.MyId;
                    long friendId = GetFriendId(friend.FriendEmail);

                    return _friendRepository.DeleteFriend(id, friendId);
                }

                private long GetFriendId(string email)
                {
                    long friendId = _friendRepository.GetFriendId(email);
                    if(friendId == 0)
                        throw new ServiceException(4007);
                    return friendId;
                }
            }
        }
    }
}