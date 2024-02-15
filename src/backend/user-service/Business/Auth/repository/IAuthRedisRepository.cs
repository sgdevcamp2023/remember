using user_service.auth.entity;

namespace user_service
{
    namespace auth
    {
        namespace repository
        {
            public interface IAuthRedisRepository
            {
                // Checksum
                bool InsertEmailAndChecksum(string key, string value, TimeSpan? expiry = null);
                bool DeleteChecksum(string key);
                string? GetChecksumByEmail(string key);
            }
        }
    }
}