using System.Collections;
using System.Diagnostics.CodeAnalysis;

namespace SmileGatewayCore.Http.Header;

public class HeaderDictionary : IDictionary<string, string>
{
    private readonly string[] EmptyKeys = Array.Empty<string>();
    private readonly string[] EmptyValues = Array.Empty<string>();

    private Dictionary<string, string> Store { get; set; }

    public HeaderDictionary()
    {
        Store = new Dictionary<string, string>(StringComparer.OrdinalIgnoreCase);
    }

    public HeaderDictionary(Dictionary<string, string> keyValuePairs)
    {
        Store = keyValuePairs;
    }

    public ICollection<string> Keys
    {
        get
        {
            if (Store == null)
                return EmptyKeys;
            return Store.Keys;
        }
    }
    public ICollection<string> Values
    {
        get
        {
            if (Store == null)
                return EmptyValues;
            return Store.Values;
        }
    }
    public int Count
    {
        get
        {
            return Store.Count;
        }
    }

    public bool IsReadOnly => throw new NotImplementedException();

    public string this[string key]
    {
        get
        {
            if (Store == null)
            {
                return String.Empty;
            }

            if (Store.TryGetValue(key, out string? value))
            {
                return value;
            }

            return String.Empty;
        }
        set
        {
            if (Store.ContainsKey(key) == false)
            {
                Store.Add(key, value);
            }
            else
            {
                Store[key] = value;
            }
        }
    }


    public void Add(string key, string value)
    {
        Store.Add(key, value);
    }

    public bool ContainsKey(string key)
    {
        if (Store == null)
            return false;

        return Store.ContainsKey(key);
    }

    public bool Remove(string key)
    {
        if (Store == null)
            return false;

        return Store.Remove(key);
    }

    public bool TryGetValue(string key, [MaybeNullWhen(false)] out string value)
    {
        if (Store == null)
        {
            value = String.Empty;
            return false;
        }

        return Store.TryGetValue(key, out value);
    }

    public void Add(KeyValuePair<string, string> item)
    {
        if (item.Key != null && item.Value != null)
        {
            Store.Add(item.Key, item.Value);
        }
    }

    public void Clear()
    {
        Store.Clear();
    }

    public bool Contains(KeyValuePair<string, string> item)
    {
        return Store.Contains(item);
    }
    public void CopyTo(KeyValuePair<string, string>[] array, int arrayIndex)
    {
        throw new NotImplementedException();
    }

    public bool Remove(KeyValuePair<string, string> item)
    {
        return Store.Remove(item.Key);
    }

    public IEnumerator<KeyValuePair<string, string>> GetEnumerator()
    {
        return Store!.GetEnumerator();
    }

    IEnumerator IEnumerable.GetEnumerator()
    {
        // return Store.GetEnumerator() ?? Enumerable.Empty<KeyValuePair<string, string>>().GetEnumerator();
        return GetEnumerator();
    }
}