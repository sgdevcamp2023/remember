using StackExchange.Redis;

namespace SmileGatewayCore.Filter.Internal;

internal class RedisRepository : IRedisRepository
{
    private readonly IDatabase _redis;

    public RedisRepository(string redisConnectionString)
    {
        var connection = ConnectionMultiplexer.Connect(redisConnectionString);
        _redis = connection.GetDatabase();
    }

    public void Delete(string key)
    {
        _redis.KeyDelete(key);    
    }

    public string? GetByKey(string key)
    {
        return _redis.StringGet(key);
    }

    public void Insert(string key, string value, TimeSpan? expiry = null)
    {
        _redis.StringSet(key, value, expiry);
    }
}