using StackExchange.Redis;
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

                public async Task<string?> GetJwtById(string email)
                {
                    try
                    {
                        return await _redisConnectionManager.GetConnection().StringGetAsync(email);
                    }
                    catch (Exception e) when (e is RedisConnectionException)
                    {
                        // 에러 처리

                        return null;
                    }
                }

                public bool InsertJwt(JwtModel jwt)
                {
                    try
                    {
                        _redisConnectionManager.GetConnection().StringSetAsync(jwt.Email, jwt.Token);
                    }
                    catch (Exception e) when (e is RedisConnectionException)
                    {
                        // 에러 처리

                        return false;
                    }
                    
                    return true;
                }

                public bool UpdateJwt(JwtModel jwt)
                {
                    return InsertJwt(jwt);
                }
            }
        }
    }
}