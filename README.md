# Minecraft <-> Terraria chat bridge

On the Minecraft side of things, this it's [PaperMC](https://papermc.io/) Plugin.  
On the Terraria side of things, this it's [TShock](https://github.com/Pryaxis/TShock) plugin.  
Works using a gRPC bi-directional stream.

## How to run

### Security

For security reasons, the client and the server must share a token of 32 bytes.  
This token can be generated with the `gen_token.ps1` or `gen_token.sh` scripts.

```bash
./gen_token.ps1
# OR
./gen_token.sh
```

### Minecraft

+ Run the server once to generate the `plugins` folder.
+ Drop the `TerraCraft.jar` into the `plugins` folder.
+ Start once again the server to generate the `plugins/TerraCraft` folder.
+ Copy the token to the `plugins/TerraCraft/config.yml` file.

### Terraria

+ Drop all the .dll files into the `ServerPlugins` folder.
+ Start the server to generate the `<tshock_install_dir>/tshock/terracraft_config.json` file.
+ Copy the token to the `terracraft_config.json` file.

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
# OR
./build.sh
```
