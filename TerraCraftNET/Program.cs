using System.Text.RegularExpressions;

WriteColor("Hello from [TerraCraftNET!]", ConsoleColor.Magenta);

static void WriteColor(string message, ConsoleColor color)
{
    var pieces = BracketRegex().Split(message);

    for (int i = 0; i < pieces.Length; i++)
    {
        string piece = pieces[i];

        if (piece.StartsWith("[") && piece.EndsWith("]"))
        {
            Console.ForegroundColor = color;
            piece = piece.Substring(1, piece.Length - 2);
        }

        Console.Write(piece);
        Console.ResetColor();
    }

    Console.WriteLine();
}

partial class Program
{
    [GeneratedRegex("(\\[[^\\]]*\\])")]
    private static partial Regex BracketRegex();
}