using user_service.auth.entity;

namespace user_service
{
    namespace auth
    {
        namespace repository
        {
            public interface IJwtRepository
            {
                bool InsertJwt(JwtModel jwt);
                bool UpdateJwt(JwtModel jwt);
                string GetJwtById(long id);
            }
        }
    }
}