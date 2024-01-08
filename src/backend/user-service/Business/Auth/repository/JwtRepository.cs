using user_service.auth.entity;
using user_service.common;

namespace user_service
{
    namespace auth
    {
        namespace repository
        {
            public class JwtRepository : IJwtRepository
            {
                RedisConnectionManager _redisConnectionManager;
                public JwtRepository(RedisConnectionManager redisConnectionManager)
                {
                    _redisConnectionManager = redisConnectionManager;
                }
                
                public JwtModel? GetJwtById(long id)
                {
                    throw new NotImplementedException();
                }

                public bool InsertJwt(JwtModel jwt)
                {
                    throw new NotImplementedException();
                }

                public bool UpdateJwt(JwtModel jwt)
                {
                    throw new NotImplementedException();
                }
            }
        }
    }
}