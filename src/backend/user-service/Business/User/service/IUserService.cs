using user_service.Business.User.dto;
using user_service.user.dto;

namespace user_service.user.service;

public interface IUserService
{
    UserDTO GetUserInfo(long userId);
    Task<string> ChangeProfile(ProfileDTO profileDTO, string traceId, string userId);
    Task ChangeName(NameDTO nameDTO, string traceId, string userId);
    void ChangePassword(PasswordDTO passwords);
    void LogOut(long headerUserId, LogoutDTO logoutDTO);
}