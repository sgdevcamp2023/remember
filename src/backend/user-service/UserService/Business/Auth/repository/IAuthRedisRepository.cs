using user_service.auth.entity;

namespace user_service
{
    namespace auth
    {
        namespace repository
        {
            public interface IAuthRedisRepository
            {
                // Refresh Token
                bool InsertIdAndRefreshToken(string key, string value, TimeSpan? expiry = null);
                bool DeleteChecksum(string key);
                string? GetRefreshTokenById(string key);

                // Checksum
                bool InsertEmailAndChecksum(string key, string value, TimeSpan? expiry = null);
                bool DeleteRefreshToken(string key);
                string? GetChecksumByEmail(string key);

                // BlackList
                bool InsertBlackListToken(string key, string value, TimeSpan? expiry = null);
                string? GetBlackListToken(string key);
            }
        }
    }
}