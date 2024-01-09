using user_service.auth.entity;

namespace user_service
{
    namespace auth
    {
        namespace repository
        {
            public interface IRedisRepository
            {
                bool InsertRedis(RedisModel jwt, TimeSpan? expiry = null);
                bool DeleteRedis(RedisModel jwt);
                Task<string?> GetJwtById(string email);
            }
        }
    }
}