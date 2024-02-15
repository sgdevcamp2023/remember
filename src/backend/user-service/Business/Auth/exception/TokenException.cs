using user_service.auth.dto;
using user_service.common.exception;

namespace user_service
{
    namespace auth
    {
        namespace exception
        {
            public class TokenException : ServiceException
            {
                public TokenDTO tokenDTO;
                public TokenException(int errorCode, TokenDTO tokenDTO) : base(errorCode)
                {
                    this.tokenDTO = tokenDTO;
                }
            }
        }
    }
}