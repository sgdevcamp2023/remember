using System.Text.Json;

namespace SmileGatewayCore.Utils;

public class ErrorCodeReader
{
    public Dictionary<int, string> ErrorCodes { get; set; } = new Dictionary<int, string>();
    public string Path { get; } = "ErrorCode.json";

    public ErrorCodeReader()
    {
        ReadErrorCode();
    }
    
    public void ReadErrorCode()
    {
        var json = JsonSerializer.Deserialize<List<ErrorCodeModel>>(File.ReadAllText(Path));
        if (json == null)
            return;
        foreach (var item in json)
        {
            string? str = JsonSerializer.Serialize(item);
            if(str == null)
                continue;

            ErrorCodes.Add(item.code, str);
        }
    }
}