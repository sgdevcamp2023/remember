
namespace user_service
{
    namespace logger
    {
        public interface IBaseLogger
        {
            void LogInformation(string service, string traceId, string method, string userId, string message, string apiAddr);
            void LogWarning(string service, string traceId, string method, string userId, string message, string apiAddr);
            void LogDebug(string service, string traceId, string method, string userId, string message, string apiAddr);
            void LogError(string service, string traceId, string method, string userId, string message, string apiAddr);
            void LogFatal(string service, string traceId, string method, string userId, string message, string apiAddr);
            void LogVerbose(string service, string traceId, string method, string userId, string message, string apiAddr);
        }
    }
}