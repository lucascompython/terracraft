using System.Runtime.InteropServices;

namespace TerraCraftNET;
// hexadecimal color codes

public partial class TerraCraft
{
    [DllImport("/home/lucas/Desktop/terracraft/TerraCraft/target/debug/libterracraft.so")]
    private static extern int mult_rust(int a, int b);


    [UnmanagedCallersOnly(EntryPoint = "Print")]
    static void Print(IntPtr messagePtr)
    {
        string message = Marshal.PtrToStringAnsi(messagePtr);
        Console.WriteLine(message + " FROM C#!");
        int result = mult_rust(1, 2);
        Console.WriteLine("1 * 2 = " + result);
    }
}



