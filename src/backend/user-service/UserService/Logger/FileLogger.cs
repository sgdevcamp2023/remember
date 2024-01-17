
namespace user_service
{
    namespace logger
    {
        public class FileLogger : IBaseLogger
        {
            public Object _lock = new object();

            public Queue<string> _queue = new Queue<string>();
            public string _path = null!;

            public IConfiguration _config;
            public FileLogger(IConfiguration config)
            {
                _config = config;
                _path = System.IO.Directory.GetCurrentDirectory() + _config["Logger:LogPath"];

                Task.Run(() =>
                {
                    while (true)
                    {
                        List<string> logs;
                        PopAll(out logs);
                        if (logs.Count > 0)
                        {
                            using (StreamWriter sw = File.AppendText(_path))
                            {
                                foreach (var log in logs)
                                {
                                    sw.WriteLine(log);
                                }
                            }
                        }
                        Thread.Sleep(1000);
                    }
                });
            }
            public void Log(string message)
            {
                Task.Run(() =>
                {
                    Push(message);
                });
            }

            private void Push(string message)
            {
                lock (_lock)
                {
                    _queue.Enqueue(message);
                }
            }

            private void PopAll(out List<string> logs)
            {
                lock (_lock)
                {
                    logs = _queue.ToList();
                }
            }
        }
    }
}