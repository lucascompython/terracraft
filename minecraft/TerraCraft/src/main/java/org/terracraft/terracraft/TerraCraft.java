package org.terracraft.terracraft;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class TerraCraft extends JavaPlugin {

    public Logger logger = getLogger();
    private Server grpcServer;

    @Override
    public void onEnable() {
        ChatService chatService = new ChatService(getServer(), logger);

        grpcServer = ServerBuilder.forPort(50051)
                .addService(chatService)
                .build();

        try {
            grpcServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("TerraCraft has been enabled!");
        getServer().getPluginManager().registerEvents(new ChatListener(logger, chatService), this);

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
