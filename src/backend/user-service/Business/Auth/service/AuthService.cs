using user_service.auth.dto;
using user_service.common;
using user_service.common.exception;
using user_service.logger;

namespace user_service
{
    namespace auth
    {
        namespace service
        {
            public class AuthService
            {
                private readonly IBaseLogger _logger;
                private readonly IUserRepository _userRepository;

                public AuthService(IBaseLogger logger, IUserRepository userRepository)
                {
                    this._logger = logger;
                    this._userRepository = userRepository;
                }
                
                public void EmailCheck(string email)
                {
                    if (this._userRepository.GetUserByEmail(email) != null)
                    {
                        throw new ServiceException(4009);
                    }
                }

                public void Register(RegisterDTO register)
                {


                }
            }
        }
    }
}