namespace user_service.logger;

public class LogModel
{
    public string Time { get; set; } = null!;
    public string Level { get; set; } = null!;
    public string Service { get; set; } = null!;
    public string Trace { get; set; } = null!;
    public string ApiAddr { get; set; } = null!;
    public string HttpMethod { get; set; } = null!;
    public string UserId { get; set; } = null!;
    public string Message { get; set; } = null!;
}