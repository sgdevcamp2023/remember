namespace SmileGatewayCore.Http.Feature;

public interface IRequestCookie
{
    public int Count { get; }

    public bool ContainKey(string key);

    public bool TryGetValue(string key, out string? value);

    public string? this[string key] { get; }
}