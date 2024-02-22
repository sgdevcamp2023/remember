using System.Text.Json;
using SmileGatewayCore.Exception;

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
        try
        {
            var json = JsonSerializer.Deserialize<List<ErrorCodeModel>>(File.ReadAllText(Path));
            if (json == null)
                throw new ConfigException(3110);

            foreach (var item in json)
            {
                string? str = JsonSerializer.Serialize(item);
                if (str == null)
                    continue;

                ErrorCodes.Add(item.code, str);
            }
        }
        catch (System.Exception e)
        {
            System.Console.WriteLine(e.Message);
            throw new ConfigException(3110);
        }
    }
}