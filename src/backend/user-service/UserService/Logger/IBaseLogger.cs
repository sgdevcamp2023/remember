
namespace user_service
{
    namespace logger
    {
        public interface IBaseLogger
        {
            public static string _path = null!;
            void Log(string message);
        }
    }
}