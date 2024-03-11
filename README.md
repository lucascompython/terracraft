# Minecraft <-> Terraria chat bridge

On the Minecraft side of things, this it's [PaperMC](https://papermc.io/) Plugin.  
On the Terraria side of things, this it's [TShock](https://github.com/Pryaxis/TShock) plugin.  
Works using a gRPC bi-directional stream.

## How to build

### Minecraft

```bash
cd minecraft/TerraCraft
./gradlew shadowJar
```

### Terraria

```bash
cd terraria/TerraCraft
./build.ps1
```
