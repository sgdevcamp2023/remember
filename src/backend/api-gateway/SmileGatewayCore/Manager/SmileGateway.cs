using SmileGatewayCore.Config;
using SmileGatewayCore.Exception;
using SmileGatewayCore.Utils.Logger;

namespace SmileGatewayCore.Manager;

public class SmileGateway
{
    internal ListenerManager ListenerManager { get; private set; } = new();
    internal ClusterManager ClusterManager { get; private set; } = new();
    private ConfigReader _configReader;
    private string _configPath;
    private Root _root = null!;
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
        _root = _configReader.Load<Root>();
        ClusterManager.Init(_root.Clusters);
        ListenerManager.ClusterManager = ClusterManager;

        ListenerManager.Init(_root.Listeners);
        ClusterManager.ListenerManager = ListenerManager;

        FileLogger.Instance.Init(_root.LogPath);
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
        if(e.ChangeType == WatcherChangeTypes.Changed)
        try
        {
            Root root = _configReader.Load<Root>();
            ListenerManager.Changed(root.Listeners);
            ClusterManager.Changed(root.Clusters);
            
            // 모두 성공시 root 바꿔치기
            _root = root;
        }
        catch (System.Exception)
        {
            // 다시 원래대로 돌려놔야 하지 않을까?
            _configReader.Save<Root>(_root);
        }
    }
}