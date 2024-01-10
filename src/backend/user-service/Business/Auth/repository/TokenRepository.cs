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
            public class TokenRepository : ITokenRepository
            {
                RedisConnectionManager _redisConnectionManager;
                FileLogger _logger = new FileLogger();
                public TokenRepository(RedisConnectionManager redisConnectionManager, FileLogger logger)
                {
                    _redisConnectionManager = redisConnectionManager;
                    _logger = logger;
                }

                public bool InsertRedis(RedisModel jwt, TimeSpan? expiry = null)
                {
                    try
                    {
                        return _redisConnectionManager.GetConnection().StringSet(jwt.Key, jwt.Value, expiry);
                    }
                    catch (Exception e) when (e is RedisConnectionException)
                    {
                        // 에러 처리
                        _logger.Log(e.Message);
                        
                        return false;
                    }
                }

                public bool DeleteRedis(RedisModel jwt)
                {
                    try
                    {
                        return _redisConnectionManager.GetConnection().KeyDelete(jwt.Key);
                    }
                    catch (Exception e) when (e is RedisConnectionException)
                    {
                        // 에러 처리
                        _logger.Log(e.Message);

                        return false;
                    }
                }

                public async Task<string?> GetJwtById(string accessToken)
                {
                    try
                    {
                        var task = _redisConnectionManager.GetConnection().StringGetAsync(accessToken);
                        await _redisConnectionManager.GetConnection().KeyDeleteAsync(accessToken);
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