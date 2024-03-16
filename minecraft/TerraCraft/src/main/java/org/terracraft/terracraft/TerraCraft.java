package org.terracraft.terracraft;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class TerraCraft extends JavaPlugin {

    public Logger logger = getLogger();
    private Server grpcServer;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        FileConfiguration config = getConfig();
        String token = config.getString("token");
        logger.info("Token: " + token);

        ChatService chatService = new ChatService(getServer(), logger);

        grpcServer = ServerBuilder.forPort(config.getInt("port"))
                .addService(chatService)
                .build();

        try {
            grpcServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new ChatListener(chatService), this);
        logger.info("TerraCraft has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        if (grpcServer != null) {
            grpcServer.shutdown();
        }

        logger.info("TerraCraft has been disabled!");
    }
}
