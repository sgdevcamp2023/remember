using user_service.user.dto;

namespace user_service.user.service;

public interface IUserService
{
    void ChangePassword(PasswordDTO passwords);
    UserDTO GetUserInfo(long userId);
    void ChangeName(NameDTO nameDTO);
    string ChangeProfile(ProfileDTO profileDTO);
}