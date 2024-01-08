using StackExchange.Redis;

namespace user_service
{
    namespace common
    {
        public class RedisConnectionManager
        {
            private string? _connectionString;
            private IDatabase? _redisDb = null;
            public RedisConnectionManager(string connectionString)
            {
                _connectionString = connectionString;
                _redisDb = ConnectionMultiplexer.Connect(_connectionString).GetDatabase();
                if(_redisDb == null)
                    throw new System.Exception("Redis connection is null");
            }

            public IDatabase GetConnection()
            {
                if(_redisDb == null)
                    throw new System.Exception("Redis connection is null");
                return _redisDb;
            }
        }
    }
}