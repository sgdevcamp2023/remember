
namespace user_service
{
    namespace logger
    {
        public class FileLogger : IBaseLogger
        {
            public void Log(string message)
            {
                Task.Run(() =>
                {
                    using (StreamWriter writer = new StreamWriter("log.txt", true))
                    {
                        writer.WriteLine(message);
                    }
                });
            }
        }
    }
}