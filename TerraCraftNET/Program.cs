using System.Text.RegularExpressions;
//WriteColor("Hello from [TerraCraftNET!]", ConsoleColor.Magenta);







partial class Program
{
    [GeneratedRegex("(\\[[^\\]]*\\])")]
    private static partial Regex BracketRegex();



    static void WriteColor(string message, ConsoleColor color)
    {
        var pieces = BracketRegex().Split(message);

        for (int i = 0; i < pieces.Length; i++)
        {
            string piece = pieces[i];

            if (piece.StartsWith("[") && piece.EndsWith("]"))
            {
                Console.ForegroundColor = color;
                piece = piece[1..^1];
            }

            Console.Write(piece);
            Console.ResetColor();
        }

        Console.WriteLine();
    }
}