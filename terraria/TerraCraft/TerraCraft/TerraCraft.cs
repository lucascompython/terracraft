using Terraria;
using TShockAPI;
using TerrariaApi.Server;
using Grpc.Core;
using Grpc.Net.Client;

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
        private ChatService.ChatServiceClient chatServiceClient;


        public TerraCraft(Main game) : base(game)
        {

        }

        public override void Initialize()
        {

            grpcChannel = GrpcChannel.ForAddress("http://localhost:50051", new GrpcChannelOptions
            {
                Credentials = ChannelCredentials.Insecure
            });
            chatServiceClient = new ChatService.ChatServiceClient(grpcChannel);



            ServerApi.Hooks.ServerChat.Register(this, OnChat);
        }

        private void OnChat(ServerChatEventArgs args)
        {
            Console.WriteLine($"Player {GetPlayerName(args.Who)} said: {args.Text}");

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
            var request = new ChatMessageRequest
            {
                Sender = playerName,
                Message = message
            };

            var call = chatServiceClient.Chat();

            await call.RequestStream.WriteAsync(request);

        }


    }
}
