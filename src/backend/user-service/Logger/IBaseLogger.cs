
namespace user_service
{
    namespace logger
    {
        public interface IBaseLogger
        {
            public static string path = null!;
            void Log(string message);
        }
    }
}