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

                public AuthService(IBaseLogger logger)
                {
                    this._logger = logger;
                }
            }
        }
    }
}