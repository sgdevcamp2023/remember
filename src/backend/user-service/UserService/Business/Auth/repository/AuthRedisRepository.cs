using Microsoft.EntityFrameworkCore.Query.SqlExpressions;
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
            public class AuthRedisRepository : IAuthRedisRepository
            {
                private RedisConnectionManager _redisConnectionManager;
                private IBaseLogger _logger;
                private string RefreshKey = $"Refresh ";
                private string ChecksumKey = $"Checksum ";
                private string BlackListKey = $"BlackList ";
                public AuthRedisRepository(RedisConnectionManager redisConnectionManager, IBaseLogger logger)
                {
                    _redisConnectionManager = redisConnectionManager;
                    _logger = logger;
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

                public bool InsertIdAndRefreshToken(string key, string value, TimeSpan? expiry = null)
                {
                    return _redisConnectionManager.Insert(RefreshKey + key, value, expiry);
                }

                public bool DeleteRefreshToken(string key)
                {
                    return _redisConnectionManager.Delete(RefreshKey + key);
                }

                public string? GetRefreshTokenById(string key)
                {
                    return _redisConnectionManager.GetStringByKey(RefreshKey + key);
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

                public bool InsertBlackListToken(string key, string value, TimeSpan? expiry = null)
                {
                    return _redisConnectionManager.Insert(BlackListKey + key, value, expiry);
                }

                public string? GetBlackListToken(string key)
                {
                    return _redisConnectionManager.GetStringByKey(BlackListKey + key);
                }
            }
        }
    }
}