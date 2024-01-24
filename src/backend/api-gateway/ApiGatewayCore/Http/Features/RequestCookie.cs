using System.Collections;

namespace ApiGatewayCore.Http.Feature;

public class RequestCookie : IRequestCookie
{
    private Dictionary<string, string> _cookies = new Dictionary<string, string>();

    public RequestCookie(string cookieString)
    {
        var cookies = cookieString.Split(';');
        foreach(string cookie in cookies)
        {
            string[] keyValue = cookie.Split('=');
            _cookies.Add(keyValue[0] , keyValue[1]);
        }
    }

    public string? this[string key] 
    { 
        get => _cookies[key];
    }

    public int Count => _cookies.Count;

    public ICollection<string> Keys => _cookies.Keys;

    public bool ContainKey(string key)
    {
        if(_cookies.ContainsKey(key))
        {
            return true;
        }
        
        return false;
    }

    public bool TryGetValue(string key, out string? value)
    {
        return _cookies.TryGetValue(key, out value);
    }
}