using user_service.common.dto;

namespace user_service.common;

public interface IStateClient
{
    public Task<ConnectsStatusDTO?> GetFriendOnlineList(UserIdsDTO userIds, string traceId, string userId);
}