using SmileGatewayCore.Config;
using SmileGatewayCore.Utils.Logger;

namespace SmileGatewayCore.Manager;

public class SmileGateway
{
    internal ListenerManager ListenerManager { get; private set; } = new();
    internal ClusterManager ClusterManager { get; private set; } = new();
    private ConfigReader _configReader;
    private string _configPath;

    public SmileGateway(string configPath)
    {
#if DEBUG
        _configPath = System.Reflection.Assembly.GetEntryAssembly()!.GetName().Name + "\\" + configPath;
        // _configPath = System.Environment.CurrentDirectory + "\\" + configPath;
#elif RELEASE
        _configPath = configPath;
#endif

        _configReader = new ConfigReader(_configPath);
    }

    public void Init()
    {
        // 초기화 
        Root config = _configReader.Load<Root>();
        ClusterManager.Init(config.Clusters);
        ListenerManager.ClusterManager = ClusterManager;

        ListenerManager.Init(config.Listeners);
        ClusterManager.ListenerManager = ListenerManager;

        FileLogger.GetInstance().Init(config.LogPath);

    }

    public void Run()
    {
        ListenerManager.Run();

        // FileSystemWatcher watcher = new FileSystemWatcher();

        // watcher.Path = System.Environment.CurrentDirectory;
        // watcher.NotifyFilter = NotifyFilters.LastWrite;
        // watcher.Filter = "*.yaml";
        // watcher.Changed += OnFileChanged;
        // watcher.EnableRaisingEvents = true;

        System.Console.WriteLine("SmileGateway is running...");
        while (true)
        {
            System.Threading.Thread.Sleep(1000);
        }
    }

    private void OnFileChanged(object sender, FileSystemEventArgs e)
    {
        Root root = _configReader.Load<Root>();
        ListenerManager.Changed(root.Listeners);
    }
}