using System.Text;
using Castle.DynamicProxy;
using Newtonsoft.Json;
using user_service.common;
using user_service.common.dto;
using user_service.common.exception;
using user_service.Controllers.dto.friend;
using user_service.friend.repository;
using user_service.intercepter;

namespace user_service.friend.service;

public class FriendService : IFriendService
{
    private IConfiguration _config;
    private IFriendRepository _friendRepository;
    private IStateClient _stateClient;
    public FriendService(IConfiguration config,
                        IFriendRepository friendRepository,
                        IStateClient stateClient,
                        LogInterceptor interceptor)
    {
        var generator = new ProxyGenerator();
        _friendRepository = generator.CreateInterfaceProxyWithTargetInterface<IFriendRepository>(friendRepository, interceptor);
        _stateClient = generator.CreateInterfaceProxyWithTargetInterface<IStateClient>(stateClient, interceptor);
        _config = config;
    }

    public async Task<List<FriendInfoDTO>> GetFriendList(long id)
    {
        List<FriendInfoDTO> friendInfos = _friendRepository.GetFriendList(id);

        UserIdsDTO userIds = new();
        foreach (FriendInfoDTO friendInfo in friendInfos)
        {
            userIds.userIds.Add(friendInfo.Id);
        }

        // 상태관리 서버에게 전송해야함
        var data = await _stateClient.GetFriendOnlineList(userIds, "traceId", "userId");
        if (data == null)
            throw new ServiceException(4025);

        foreach (FriendInfoDTO friendInfo in friendInfos)
        {
            if (data.connectionStates[friendInfo.Id.ToString()] == "online")
                friendInfo.IsOnline = true;
            else
                friendInfo.IsOnline = false;
        }

        return friendInfos;
    }

    public List<FriendInfoDTO> GetSendRequestList(long id)
    {
        return _friendRepository.ShowAllSendRequestList(id);
    }

    public List<FriendInfoDTO> GetReceiveRequestList(long id)
    {
        return _friendRepository.ShowAllReceiveRequesttList(id);
    }

    public void SendFriendAddRequest(FriendDTO friend)
    {
        long id = friend.MyId;
        long friendId = GetFriendId(friend.FriendEmail);

        if (id == friendId)
            throw new ServiceException(4026);

        if (_friendRepository.CheckAlreadyFriend(id, friendId))
            throw new ServiceException(4016);

        if (!_friendRepository.SendFriendRequest(id, friendId))
            throw new ServiceException(4017);
    }

    public void CancleFriendAddRequest(FriendDTO friend)
    {
        long id = friend.MyId;
        long friendId = GetFriendId(friend.FriendEmail);

        if (id == friendId)
            throw new ServiceException(4026);

        if (!_friendRepository.CancleFriendRequest(id, friendId))
            throw new ServiceException(4020);
    }

    public void AcceptFriendAddRequest(FriendDTO friend)
    {
        long id = friend.MyId;
        long friendId = GetFriendId(friend.FriendEmail);

        if (id == friendId)
            throw new ServiceException(4026);

        if (!_friendRepository.AcceptFriendRequest(id, friendId))
            throw new ServiceException(4018);
    }

    public void RefuseFriendAddRequest(FriendDTO friend)
    {
        long id = friend.MyId;
        long friendId = GetFriendId(friend.FriendEmail);

        if (id == friendId)
            throw new ServiceException(4026);

        if (!_friendRepository.RefuseFriendRequest(id, friendId))
            throw new ServiceException(4019);
    }

    public bool DeleteFriend(FriendDTO friend)
    {
        long id = friend.MyId;
        long friendId = GetFriendId(friend.FriendEmail);

        if (id == friendId)
            throw new ServiceException(4027);

        return _friendRepository.DeleteFriend(id, friendId);
    }

    private long GetFriendId(string email)
    {
        long friendId = _friendRepository.GetFriendId(email);
        if (friendId == 0)
            throw new ServiceException(4007);

        return friendId;
    }
}