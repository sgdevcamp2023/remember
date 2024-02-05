namespace user_service.logger;

public class LogModel
{
    public string timestamp { get; set; } = null!;
    public string level { get; set; } = null!;
    public string service { get; set; } = null!;
    public string trace { get; set; } = null!;
    public string apiAddr { get; set; } = null!;
    public string httpMethod { get; set; } = null!;
    public string userId { get; set; } = null!;
    public string message { get; set; } = null!;

    public override string ToString()
    {
        return $"{{\"@timestamp\":\"{timestamp} \",\"level\":\"{level}\",\"service\":\"{service}\",\"trace\":\"{trace}\",\"apiAddr\":\"{apiAddr}\",\"httpMethod\":\"{httpMethod}\",\"userId\":\"{userId}\",\"message\":\"{message}\"}}";
    }
}