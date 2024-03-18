# NORMAL SHRINKING THINGS
-keep class org.terracraft.terracraft.** { *; }
-keep class io.grpc.** { *; }
-keep class org.bukkit.event.EventHandler { *; }
#-keep class org.bukkit.event.Listener { *; }



# OPTIMIZATIONS THINGS
#-keep class com.google.common.base.** { *; }
#-keep class com.google.common.util.** { *; }
#-keep class com.google.common.collect.** { *; }
#-keep class com.google.common.** { *; }
#-keep class org.slf4j.** { *; }

#-optimizeaggressively
#-mergeinterfacesaggressively
#-allowaccessmodification