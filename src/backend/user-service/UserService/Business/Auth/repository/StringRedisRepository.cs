using StackExchange.Redis;
using user_service.auth.entity;
using user_service.common;
using user_service.logger;

namespace user_service
{
    namespace auth
    {
        namespace repository
        {
            public class StringRedisRepository : IStringRedisRepository
            {
                private RedisConnectionManager _redisConnectionManager;
                private IBaseLogger _logger;
                public StringRedisRepository(RedisConnectionManager redisConnectionManager, IBaseLogger logger)
                {
                    _redisConnectionManager = redisConnectionManager;
                    _logger = logger;
                }

                public bool InsertRedis(RedisModel model, TimeSpan? expiry = null)
                {
                    if(expiry == null)
                        return _redisConnectionManager.Insert(model.Key, model.Value);
                    return _redisConnectionManager.Insert(model.Key, model.Value, expiry);
                }

                public bool DeleteRedis(string key)
                {
                    return _redisConnectionManager.Delete(key);
                }

                public string? GetStringById(string key)
                {
                    return _redisConnectionManager.GetStringByKey(key);
                }
            }
        }
    }
}