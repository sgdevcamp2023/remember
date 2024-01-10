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
                    try
                    {
                        return _redisConnectionManager.GetConnection().StringSet(model.Key, model.Value, expiry);
                    }
                    catch (Exception e) when (e is RedisConnectionException)
                    {
                        // 에러 처리
                        _logger.Log(e.Message);
                        
                        return false;
                    }
                }

                public bool DeleteRedis(RedisModel model)
                {
                    try
                    {
                        return _redisConnectionManager.GetConnection().KeyDelete(model.Key);
                    }
                    catch (Exception e) when (e is RedisConnectionException)
                    {
                        // 에러 처리
                        _logger.Log(e.Message);

                        return false;
                    }
                }

                public async Task<string?> GetStringById(string key)
                {
                    try
                    {
                        var task = _redisConnectionManager.GetConnection().StringGetAsync(key);
                        await _redisConnectionManager.GetConnection().KeyDeleteAsync(key);
                        return await task;
                    }
                    catch (Exception e) when (e is RedisConnectionException)
                    {
                        // 에러 처리
                        _logger.Log(e.Message);
                        return null;
                    }
                }
            }
        }
    }
}