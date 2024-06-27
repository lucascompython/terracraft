-keep class org.terracraft.terracraft.** { *; }
# TODO: See if we can remove this and keep only the classes we need from io.grpc
-keep class io.grpc.** { *; }

# For the chat listener
-keep class org.bukkit.event.EventHandler { *; }


## Things needed for obfuscation

-keep class org.bukkit.plugin.java.JavaPlugin { *; }

-keep class org.bukkit.configuration.file.FileConfiguration { *; }
-keep interface org.bukkit.Server { *; }
-keep interface org.bukkit.plugin.PluginManager { *; }
-keep interface org.bukkit.event.Listener { *; }
-keep interface org.bukkit.plugin.Plugin { *; }

-keep class org.slf4j.LoggerFactory { *; }

-keep interface net.kyori.adventure.text.TextComponent { *; }
-keep interface net.kyori.adventure.text.Component { *; }


-keep class io.papermc.paper.event.player.AsyncChatEvent { *; }
-keep interface org.bukkit.entity.Player { *; }

-keepattributes RuntimeVisibleAnnotations
