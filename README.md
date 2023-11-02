# Terracraft

## Minecraft <-> Terraria chat bridge

The "brain" of the project is a Rust shared library. It is loaded by the JVM and Dotnet.

## JVM Interop

For now I am using the JNI. But I will try [Project Panama](https://openjdk.org/projects/panama/).

## .NET Interop

.NET Calling Rust is done with [P/Invoke](https://docs.microsoft.com/en-us/dotnet/standard/native-interop/pinvoke).  
Rust Calling .NET is done exposing methods with UnmanagedCallersOnly.  
Might switch to [COM](https://learn.microsoft.com/en-us/dotnet/standard/native-interop/cominterop).
