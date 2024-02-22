using user_service.common.dto;

namespace user_service.common;

public interface ICommunityClient
{
    public Task<bool> RegisterUserAsync(CommunityUserDTO userModel, string traceId);
    public Task<bool> ChangeProfileAsync(CommunityProfileDTO communityDTO, string traceId, string userId);
    public Task<bool> ChangeNameAsync(CommunityNameDTO communityDTO, string traceId, string userId);
    public Task<bool> CreateDMRoomAsync(CommunityRoomCreateDTO communityDTO, string traceId, string userId);
}