
namespace user_service
{
    namespace logger
    {
        public class FileLogger : IBaseLogger
        {
            public static Object _lock = new object();
            public static string _path = null!;
            public void Log(string message)
            {
                Task.Run(() =>
                {
                    lock (_lock)
                    {
                        System.Console.WriteLine(message);
                        // using (StreamWriter writer = new StreamWriter(_path, true))
                        // {
                        //     writer.WriteLine(message);
                        // }
                    }
                });
            }
        }
    }
}