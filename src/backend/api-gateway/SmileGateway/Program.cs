using SmileGatewayCore.Manager;

SmileGateway gateway = new SmileGateway("SmileGatewayConfig.yaml");

gateway.Init();

gateway.Run();