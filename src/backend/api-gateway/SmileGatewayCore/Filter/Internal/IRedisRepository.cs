namespace SmileGatewayCore.Filter.Internal;

internal interface IRedisRepository
{
    public void Insert(string key, string value, TimeSpan? expiry = null);
    public string? GetByKey(string key);
    public void Delete(string key);
}