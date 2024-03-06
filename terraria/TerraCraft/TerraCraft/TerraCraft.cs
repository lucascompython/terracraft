using Terraria;
using TShockAPI;
using TerrariaApi.Server;


namespace TerraCraft
{
    [ApiVersion(2, 1)]
    public class TerraCraft : TerrariaPlugin
    {
        public override string Name => "TerraCraft";
        public override Version Version => new(1, 0);
        public override string Author => "Lucas de Linhares";
        public override string Description => "A plugin that makes a chat bridge with a Minecraft Server";

        public TerraCraft(Main game) : base(game)
        {

        }

        public override void Initialize()
        {
            ServerApi.Hooks.ServerChat.Register(this, OnChat);
        }

        private void OnChat(ServerChatEventArgs args)
        {
            Console.WriteLine($"Player {GetPlayerName(args.Who)} said: {args.Text}");
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

    }
}
