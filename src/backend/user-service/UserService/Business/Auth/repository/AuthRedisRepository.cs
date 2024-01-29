using user_service.common;

namespace user_service
{
    namespace auth
    {
        namespace repository
        {
            public class AuthRedisRepository : IAuthRedisRepository
            {
                private RedisConnectionManager _redisConnectionManager;
                private string ChecksumKey = $"Checksum ";
                public AuthRedisRepository(RedisConnectionManager redisConnectionManager)
                {
                    _redisConnectionManager = redisConnectionManager;
                }

                public bool InsertRedis(string key, string value, TimeSpan? expiry = null)
                {
                    return _redisConnectionManager.Insert(key, value, expiry);
                }

                public bool DeleteRedis(string key)
                {
                    return _redisConnectionManager.Delete(key);
                }

                public string? GetStringById(string key)
                {
                    return _redisConnectionManager.GetStringByKey(key);
                }

                public bool InsertEmailAndChecksum(string key, string value, TimeSpan? expiry = null)
                {
                    return _redisConnectionManager.Insert(ChecksumKey + key, value, expiry);
                }
                public bool DeleteChecksum(string key)
                {
                    return _redisConnectionManager.Delete(ChecksumKey + key);
                }

                public string? GetChecksumByEmail(string key)
                {
                    return _redisConnectionManager.GetStringByKey(ChecksumKey + key);
                }
            }
        }
    }
}