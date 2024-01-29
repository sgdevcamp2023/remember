using System.Collections.Concurrent;
using System.Text.Json;
using Timer = System.Timers.Timer;

namespace user_service
{
    namespace logger
    {
        public class FileLogger : IBaseLogger
        {
            public ConcurrentQueue<LogModel> _queue = new ConcurrentQueue<LogModel>();
            public string _path = null!;

            public IConfiguration _config;
            public FileLogger(IConfiguration config)
            {
                _config = config;
                _path = System.IO.Directory.GetCurrentDirectory() + _config["Logger:LogPath"];
                Timer timer = new Timer();
                timer.Interval = 1000;
                timer.Elapsed += (sender, args) =>
                {
                    List<LogModel> logs;
                    PopAll(out logs);
                    if (logs.Count > 0)
                    {
                        using (StreamWriter sw = File.AppendText(_path))
                        {
                            foreach (var log in logs)
                            {
                                string json = JsonSerializer.Serialize(log);
                                sw.WriteLine(json);
                            }
                        }
                    }
                };
                timer.Start();
            }
            private void Log(string type, string service, string traceId, string method, string userId, string message, string apiAddr)
            {
                string time = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff");
                
                _queue.Enqueue(new LogModel
                {
                    Time = time,
                    Level = type,
                    Service = service,
                    Trace = traceId,
                    HttpMethod = method,
                    UserId = userId,
                    Message = message,
                    ApiAddr = ""
                });
            }

            private void PopAll(out List<LogModel> logs)
            {
                logs = new List<LogModel>();
                while(_queue.TryDequeue(out LogModel? data))
                {
                    logs.Add(data!);
                }
            }

            public void LogInformation(string service, string traceId, string method, string userId, string message, string apiAddr)
            {
                Log("INFO", service, traceId, method, userId, message, apiAddr);
            }

            public void LogWarning(string service, string traceId, string method, string userId, string message, string apiAddr)
            {
                Log("WARN", service, traceId, method, userId, message, apiAddr);
            }
            public void LogDebug(string service, string traceId, string method, string userId, string message, string apiAddr)
            {
                Log("DEBUG", service, traceId, method, userId, message, apiAddr);
            }

            public void LogError(string service, string traceId, string method, string userId, string message, string apiAddr)
            {
                Log("ERROR", service, traceId, method, userId, message, apiAddr);
            }

            public void LogFatal(string service, string traceId, string method, string userId, string message, string apiAddr)
            {
                Log("FATAL", service, traceId, method, userId, message, apiAddr);
            }

            public void LogVerbose(string service, string traceId, string method, string userId, string message, string apiAddr)
            {
                Log("VERBOSE", service, traceId, method, userId, message, apiAddr);
            }
        }
    }
}