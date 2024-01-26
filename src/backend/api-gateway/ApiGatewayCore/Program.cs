using ApiGatewayCore.Config;
using ApiGatewayCore.Manager;
using Microsoft.AspNetCore.Http;

namespace ApiGatewayCore;

public class Program
{
    public static void Main(string[] args)
    {
        var gateway = new SmileGateway("ApiGatewayConfig.yaml");
        gateway.Init();
        gateway.Run();
    }
}

// Root root = new Root(){
//     Listeners = new List<Listener>(){
//         new Listener(){
//             Name = "listener1",
//             Address = new SocketAddress(){
//                 Address = "127.0.0.1",
//                 Port = 8080
//                 },
//             RouteConfig = new RouteConfig(){
//                 Services = new List<string>(){
//                     "service1",
//                     "service2",
//                     "service3"
//                 }
//             }
//         }
//     },
//     Clusters = new List<Cluster>(){
//         new Cluster(){
//             Services = new List<Service>(){
//                 new Service(){
//                     Name = "service1",
//                     Protocol = "http",
//                     Address = new SocketAddress(){
//                         Address = "127.0.0.1",
//                         Port = 8081
//                     },
//                     CustomFilter = new CustomFilter(){
//                         Name = "customFilter1"
//                     },
//                     Prefix = "/service1",
//                     ConnectTimeout = "1s"
//                 },
//                 new Service(){
//                     Name = "service2",
//                     Protocol = "http",
//                     Address = new SocketAddress(){
//                         Address = "127.0.0.1",
//                         Port = 8082
//                     },
//                     CustomFilter = null,
//                     Prefix = "/service2",
//                     ConnectTimeout = "1s"
//                 },
//             }
//         }
//     }
// };
// yamlReaderWriter.Save(root);