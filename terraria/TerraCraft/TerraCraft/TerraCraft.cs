using Terraria;
using TShockAPI;
using TerrariaApi.Server;
using Grpc.Core;
using Grpc.Net.Client;
using Microsoft.Xna.Framework;

namespace TerraCraft
{
    [ApiVersion(2, 1)]
    public class TerraCraft : TerrariaPlugin
    {
        public override string Name => "TerraCraft";
        public override Version Version => new(1, 0);
        public override string Author => "Lucas de Linhares";
        public override string Description => "A plugin that makes a chat bridge with a Minecraft Server";


        private GrpcChannel grpcChannel;
        AsyncDuplexStreamingCall<ChatMessage, ChatMessage> chat;

        public TerraCraft(Main game) : base(game)
        {

        }

        public override void Initialize()
        {

            grpcChannel = GrpcChannel.ForAddress("http://localhost:50051", new GrpcChannelOptions
            {
                Credentials = ChannelCredentials.Insecure
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
