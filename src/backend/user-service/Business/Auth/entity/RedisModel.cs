
namespace user_service
{
    namespace auth
    {
        namespace entity
        {
            // In Use Redis Key - AccessToken, Value - Refresh Token
            public class RedisModel
            {
                public RedisModel(string key, string value)
                {
                    this.Key = key;
                    this.Value = value;
                }

                public string Key { get; set; } = null!;
                public string Value { get; set; } = null!;
            }
        }
    }
}