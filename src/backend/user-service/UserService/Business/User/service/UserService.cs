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
                
                public void ChangePassword(long id, PasswordDTO passwords)
                {
                    UserModel? user = _userRepository.GetUserById(id);
                    if(user == null)
                        throw new ServiceException(4007);

                    string currentPassword = Utils.SHA256Hash(passwords.Password);
                    
                    if(user.Password != currentPassword)
                        throw new ServiceException(4008);

                    _userRepository.UpdatePassword(id, Utils.SHA256Hash(passwords.NewPassword));
                }

                public UserDTO GetUserInfo(long id)
                {
                    UserModel? user = _userRepository.GetUserById(id);
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

                public void ChangeName(long id, string newName)
                {
                    _userRepository.UpdateName(id, newName);
                }

                public void ChangeProfile(long id, string newProfileUrl)
                {
                    _userRepository.UpdateProfile(id, newProfileUrl);
                }
            }
        }
    }
}