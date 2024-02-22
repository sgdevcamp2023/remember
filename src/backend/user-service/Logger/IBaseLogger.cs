
namespace user_service
{
    namespace logger
    {
        public interface IBaseLogger
        {
            void LogInformation(string traceId, string method, string userId, string message, string apiAddr);
            void LogWarning(string traceId, string method, string userId, string message, string apiAddr);
            void LogDebug(string traceId, string method, string userId, string message, string apiAddr);
            void LogError(string traceId, string method, string userId, string message, string apiAddr);
            void LogFatal(string traceId, string method, string userId, string message, string apiAddr);
            void LogVerbose(string traceId, string method, string userId, string message, string apiAddr);
        }
    }
}