package org.terracraft.terracraft;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import io.grpc.stub.StreamObserver;
import java.util.logging.Logger;

import org.terracraft.grpc.ChatServiceGrpc;
import org.terracraft.grpc.Chat.ChatMessageRequest;
import org.terracraft.grpc.Chat.ChatMessageResponse;

public final class TerraCraft extends JavaPlugin {

    public Logger logger = getLogger();
//    private ChatServiceGrpc.ChatServiceStub chatServiceStub;
    private Server grpcServer;

    @Override
    public void onEnable() {
        // Plugin startup logic

        grpcServer = ServerBuilder.forPort(50051)
                .addService(new ChatService(getServer()))
                .build();


        try {
            grpcServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


        logger.info("TerraCraft has been enabled!");
        // Register chat event listener
        getServer().getPluginManager().registerEvents(new ChatListener(logger), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        if (grpcServer != null) {
            grpcServer.shutdown();
        }
//        if (chatServiceStub != null){
//            chatServiceStub.getChannel();
//        }

        logger.info("TerraCraft has been disabled!");
    }
}
