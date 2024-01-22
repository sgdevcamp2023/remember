using Org.BouncyCastle.Crypto.Utilities;
using user_service.user.dto;
using user_service.common;
using user_service.common.exception;
using Microsoft.AspNetCore.Mvc;

namespace user_service
{
    namespace user
    {
        namespace service
        {
            public class UserService
            {
                private IUserRepository _userRepository;
                public UserService(IUserRepository userRepository)
                {
                    _userRepository = userRepository;
                }
                
                public void ChangePassword(PasswordDTO passwords)
                {
                    long userId = passwords.UserId;
                    UserModel? user = _userRepository.GetUserById(userId);
                    if(user == null)
                        throw new ServiceException(4007);

                    string currentPassword = Utils.SHA256Hash(passwords.Password);
                    
                    if(user.Password != currentPassword)
                        throw new ServiceException(4008);

                    _userRepository.UpdatePassword(userId, Utils.SHA256Hash(passwords.NewPassword));
                }

                public UserDTO GetUserInfo(long userId)
                {
                    UserModel? user = _userRepository.GetUserById(userId);
                    if(user == null)
                        throw new ServiceException(4007);

                    return new UserDTO()
                    {
                        Id = user.Id,
                        Email = user.Email,
                        Name = user.Name,
                        ProfileUrl = user.Profile
                    };
                }

                public void ChangeName(NameDTO nameDTO)
                {
                    _userRepository.UpdateName(nameDTO.UserId, nameDTO.NewName);
                }

                public void ChangeProfile(ProfileDTO profileDTO)
                {
                    _userRepository.UpdateProfile(profileDTO.UserId, profileDTO.NewProfile);
                }
            }
        }
    }
}