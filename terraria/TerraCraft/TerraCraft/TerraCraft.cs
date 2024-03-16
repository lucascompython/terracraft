using Terraria;
using TShockAPI;
using TerrariaApi.Server;
using Grpc.Core;
using Grpc.Net.Client;
using Microsoft.Xna.Framework;
using System.Text.Json;

namespace TerraCraft
{

    public struct Config
    {
        public int MinecraftServerPort { get; set; }
        public string? MinecraftServerAddress { get; set; }
        public string? Token { get; set; }
    }

    [ApiVersion(2, 1)]
    public sealed class TerraCraft : TerrariaPlugin
    {
        public override string Name => "TerraCraft";
        public override Version Version => new(1, 0);
        public override string Author => "Lucas de Linhares";
        public override string Description => "A plugin that makes a chat bridge with a Minecraft Server";

        private readonly string _configPath = Path.Combine(TShock.SavePath, "terracraft_config.json");
        private Config config;

        private GrpcChannel grpcChannel;
        private AsyncDuplexStreamingCall<ChatMessage, ChatMessage> chat;

        public TerraCraft(Main game) : base(game)
        {
            if (!File.Exists(_configPath))
            {
                config = new Config
                {
                    MinecraftServerPort = 50051,
                    MinecraftServerAddress = "http://localhost",
                    Token = "your_token_here"
                };

                File.WriteAllText(_configPath, JsonSerializer.Serialize(config, new JsonSerializerOptions
                {
                    WriteIndented = true
                }));
            }
            else
            {
                config = JsonSerializer.Deserialize<Config>(File.ReadAllText(_configPath));
            }
        }

        public override void Initialize()
        {
            grpcChannel = GrpcChannel.ForAddress($"{config.MinecraftServerAddress}:{config.MinecraftServerPort}", new GrpcChannelOptions
            {
                Credentials = ChannelCredentials.Insecure,
            });
            var chatServiceClient = new ChatService.ChatServiceClient(grpcChannel);
            chat = chatServiceClient.Chat();

            ServerApi.Hooks.ServerChat.Register(this, OnChat);
            Task.Run(ReadMessage);
        }

        private void OnChat(ServerChatEventArgs args)
        {

            SendChatMessageAsync(GetPlayerName(args.Who), args.Text);
        }

        private static string GetPlayerName(int who) => TShock.Players[who].Name;


        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                ServerApi.Hooks.ServerChat.Deregister(this, OnChat);
            }

            base.Dispose(disposing);
        }


        private async void SendChatMessageAsync(string playerName, string message)
        {
            var request = new ChatMessage
            {
                Sender = playerName,
                Message = message,
                ComesFromServer = false
            };

            await chat.RequestStream.WriteAsync(request);

        }

        private async Task ReadMessage()
        {
            while (await chat.ResponseStream.MoveNext())
            {
                var chatMessage = chat.ResponseStream.Current;
                TShock.Utils.Broadcast($"[Minecraft] {chatMessage.Sender}: {chatMessage.Message}", Color.White);
            }
        }
    }
}
