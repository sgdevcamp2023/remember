using Microsoft.EntityFrameworkCore.Metadata.Internal;
using StackExchange.Redis;
using user_service.common.exception;
using user_service.logger;

namespace user_service
{
    namespace common
    {
        public class RedisConnectionManager
        {
            private IConfiguration _config;
            private IDatabase _redisDb = null!;
            private ConnectionMultiplexer? _redisConnection = null;
            private IBaseLogger _logger;
            private string _connectionString;
            public RedisConnectionManager(IConfiguration config, IBaseLogger logger)
            {
                _config = config;
                _logger = logger;
                _connectionString = _config.GetConnectionString("RedisConnection");

                Connect();
            }

            public IDatabase GetConnection()
            {
                if (_redisConnection == null || !_redisConnection.IsConnected || _redisDb.IsConnected(default(RedisKey)) == false)
                    Connect();
                
                return _redisDb;
            }

            private void Connect()
            {
                try
                {
                    _redisConnection = ConnectionMultiplexer.Connect(_connectionString);
                    if (_redisConnection == null)
                        throw new CommonException("Redis connection is null");

                    _redisDb = _redisConnection.GetDatabase();
                    if (_redisDb == null)
                        throw new CommonException("Redis connection is null");
                }
                catch (Exception e)
                {
                    _logger.Log("Redis connection error");

                    Console.WriteLine(e.Message);
                }
            }

            public bool Insert(string key, string value)
            {
                try
                {
                   if (_redisConnection == null || !_redisConnection.IsConnected || _redisDb.IsConnected(default(RedisKey)) == false)
                        Connect();
                    
                    return _redisDb.StringSet(key, value);
                }
                catch (Exception e)
                {
                    _logger.Log("Redis insert error");

                    Console.WriteLine(e.Message);
                    return false;
                }
            }

            public bool Delete(string key)
            {
                try
                {
                    if (_redisConnection == null || !_redisConnection.IsConnected || _redisDb.IsConnected(default(RedisKey)) == false)
                        Connect();
                    
                    return _redisDb.KeyDelete(key);
                }
                catch (Exception e)
                {
                    _logger.Log("Redis delete error");

                    Console.WriteLine(e.Message);
                    return false;
                }
            }
            
            public string? GetStringByKeyAsync(string key)
            {
                try
                    {
                        if (_redisConnection == null || !_redisConnection.IsConnected || _redisDb.IsConnected(default(RedisKey)) == false)
                            Connect();
                        return _redisDb.StringGet(key);
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