using YamlDotNet.Serialization;
using YamlDotNet.Serialization.NamingConventions;

namespace SmileGatewayCore.Config;

internal class ConfigReader
{
    private string _path { get; set; } = null!;
    private readonly IDeserializer deserializer = new DeserializerBuilder()
                                                    .WithNamingConvention(HyphenatedNamingConvention.Instance)
                                                    .Build();
    private readonly ISerializer serializer = new SerializerBuilder()
                                                .WithNamingConvention(HyphenatedNamingConvention.Instance)
                                                .Build();

    private string directoryPath = "C:\\github\\remember\\src\\backend\\api-gateway\\SmileGateway\\";
    public ConfigReader(string path)
    {
        // _path = ".\\SmileGateway\\" + path;
        _path = directoryPath + path;
    }

    public T Load<T>()
    {
        using (var stream = File.Open(_path, FileMode.Open, FileAccess.Read))
        {
            using (var reader = new StreamReader(stream))
            {
                return deserializer.Deserialize<T>(reader);
            }
        }
    }

    public bool Save<T>(T value)
    {
        using (var stream = File.Create(_path))
        {
            using (var writer = new StreamWriter(stream))
            {
                serializer.Serialize(writer, value);
                return true;
            }
        }
    }
}