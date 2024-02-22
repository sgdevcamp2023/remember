using System.Collections.Concurrent;
using System.Text.Json;
using Timer = System.Timers.Timer;

namespace SmileGatewayCore.Utils.Logger;

public class FileLogger
{
    public static FileLogger Instance { get; set; } = new FileLogger();

    private string _serviceName = "gateway-service";
    public ConcurrentQueue<LogModel> _queue = new ConcurrentQueue<LogModel>();
    public string _path = "";

    public void Init(string path)
    {
        // 경로 측정 및 확인 해야함.
        
        _path = path;

        Timer timer = new Timer();
        timer.Interval = 500;
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
        // Lock을 써서 관리?
        // Count만큼 할당받은 후에 처리?
        logs = new List<LogModel>(_queue.Count);
        while (_queue.TryDequeue(out LogModel? data))
        {
            logs.Add(data!);
        }
    }

    public void LogInformation(string traceId, string method, string userId, string message, string apiAddr)
    {
        Log("INFO", _serviceName, traceId, method, userId, message, apiAddr);
    }

    public void LogWarning(string traceId, string method, string userId, string message, string apiAddr)
    {
        Log("WARN", _serviceName, traceId, method, userId, message, apiAddr);
    }
    public void LogDebug(string traceId, string method, string userId, string message, string apiAddr)
    {
        Log("DEBUG", _serviceName, traceId, method, userId, message, apiAddr);
    }

    public void LogError(string traceId, string method, string userId, string message, string apiAddr)
    {
        Log("ERROR", _serviceName, traceId, method, userId, message, apiAddr);
    }

    public void LogFatal(string traceId, string method, string userId, string message, string apiAddr)
    {
        Log("FATAL", _serviceName, traceId, method, userId, message, apiAddr);
    }

    public void LogVerbose(string traceId, string method, string userId, string message, string apiAddr)
    {
        Log("VERBOSE", _serviceName, traceId, method, userId, message, apiAddr);
    }
}