# NORMAL SHRINKING THINGS
-keep class org.terracraft.terracraft.** { *; }
-keep class org.terracraft.terracraft.Terracraft { *; }
-keep class io.grpc.** { *; }

-keep class org.bukkit.event.EventHandler { *; }
-keep class org.bukkit.event.EventHandler.** { *; }
-keep class org.bukkit.plugin.java.JavaPlugin { *; }
#-keep class org.bukkit.plugin.java.PluginBase { *; }
-keep interface org.bukkit.plugin.PluginManager { *; }
-keep interface org.bukkit.event.Listener { *; }
-keep interface org.bukkit.plugin.Plugin { *; }
-keep interface org.bukkit.Server { *; }
-keeppackagenames io.grpc.**

-keep class org.bukkit.configuration.file.FileConfiguration { *; }
-keep class org.bukkit.configuration.file.YamlConfiguration { *; }
-dontusemixedcaseclassnames
-useuniqueclassmembernames
-keepparameternames

-printmapping out.map
-keep class net.kyori.adventure.text.format.** { *; }

# static TextComponent text(final String content) 
-keep class net.kyori.adventure.text.Component { *; }
-keep class net.kyori.adventure.text.TextComponent { *; }
#-keep class net.kyori.adventure.text.Component.* { *; }

#-keep class net.kyori.adventure.text.Component
-keep class net.kyori.adventure.Component {
     static net.kyori.adventure.text.TextComponent text (java.lang.String);
}

-keep class io.papermc.paper.event.player.AsyncChatEvent { *; }
-keep interface org.bukkit.entity.Player { *; }
-keep class org.bukkit.** { *; }
-keep class io.papermc.** { *; }




# OPTIMIZATIONS THINGS
#-keep class com.google.common.base.** { *; }
#-keep class com.google.common.util.** { *; }
#-keep class com.google.common.collect.** { *; }
#-keep class com.google.common.** { *; }
-keep class org.slf4j.** { *; }

#-optimizeaggressively
#-mergeinterfacesaggressively
#-allowaccessmodification



# R8 THINGS
#-keep class org.jetbrains.** { *; }
#-keep class sun.** { *; }
#-keep class java.** { *; }
#-keep class * {
     #*;
#}
#-keep interface  * {
     #*;
#}


#-ignorewarnings
#-allowaccessmodification

#-keep class org.bukkit.** { *; }
#-keep class org.bukkit.plugin.** { *; }
#-keep class org.bukkit.plugin.java.** { *; }
#-keep class org.bukkit.plugin.java.PluginClassloader.** { *; }
#-keep class org.bukkit.Server.** { *; }
#-keep class org.bukkit.plugin.** { *; }
#-keep class org.bukkit.craftbukkit.v1_20_R3.** { *; }
