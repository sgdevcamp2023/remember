using System.Text;
using Castle.DynamicProxy;
using Newtonsoft.Json;
using user_service.Business.Friend.dto;
using user_service.common.exception;
using user_service.Controllers.dto.friend;
using user_service.friend.repository;
using user_service.intercepter;

namespace user_service.friend.service;

public class FriendService : IFriendService
{
    private IConfiguration _config;
    private IFriendRepository _friendRepository;
    private HttpClient _httpClient;
    public FriendService(IConfiguration config,
                        IFriendRepository friendRepository,
                        LogInterceptor interceptor)
    {
        var generator = new ProxyGenerator();
        _friendRepository = generator.CreateInterfaceProxyWithTargetInterface<IFriendRepository>(friendRepository, interceptor);
        _config = config;
        _httpClient = new HttpClient();
        _httpClient.BaseAddress = new Uri("http://10.99.14.176:9090/api/state/direct/user/info");
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
        using StringContent jsonContent = new(
            JsonConvert.SerializeObject(userIds),
            Encoding.UTF8,
            "application/json");

        CancellationTokenSource cts = new CancellationTokenSource();
        cts.CancelAfter(100); // Cancel after 1 second
        CancellationToken cancellationToken = cts.Token;
        try
        {
            var response = await _httpClient.PostAsync("", jsonContent, cancellationToken);
            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                var result = await response.Content.ReadAsStringAsync();
                ConnectsStatusDTO? data = JsonConvert.DeserializeObject<ConnectsStatusDTO>(result);
                if (data == null)
                    throw new ServiceException(4025);

                foreach (FriendInfoDTO friendInfo in friendInfos)
                {
                    if (data.connectionStates[friendInfo.Id.ToString()] == "online")
                        friendInfo.IsOnline = true;
                    else
                        friendInfo.IsOnline = false;
                }
            }
            else
            {
                throw new ServiceException(4025);
            }
        }
        catch (System.Exception)
        {

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