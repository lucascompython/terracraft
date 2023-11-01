# Terracraft

## Minecraft <-> Terraria chat bridge

The "brain" of the project is a Rust shared library. It is loaded by the JVM and Dotnet.

## JVM Interop

For now I am using the JNI. But I will try [Project Panama](https://openjdk.org/projects/panama/).
