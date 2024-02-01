﻿// See https://aka.ms/new-console-template for more information

using SmileGatewayCore.Manager;

public class Program
{
    public static void Main(string[] args)
    {
        SmileGateway gateway = new SmileGateway("SmileGatewayConfig.yaml");
        
        gateway.Init();

        gateway.Run();
    }
}