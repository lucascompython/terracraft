package org.terracraft.terracraft;

import io.grpc.stub.StreamObserver;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.terracraft.grpc.Chat;
import org.terracraft.grpc.ChatServiceGrpc;

public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {


    private final Server server;

    public ChatService(Server server) {
        this.server = server;
    }




    @Override
    public StreamObserver<Chat.ChatMessageRequest> chat(StreamObserver<Chat.ChatMessageResponse> responseObserver) {
        return new StreamObserver<Chat.ChatMessageRequest>() {
            @Override
            public void onNext(Chat.ChatMessageRequest request) {
                // Process incoming message
                String sender = request.getSender();
                String message = request.getMessage();

                System.out.println("Received from " + sender + ": " + message + " (Minecraft)");

                server.broadcast(Component.text("[Terraria] <" + sender + ">" + " " + message));

                // Your logic to handle the message and send a response
                Chat.ChatMessageResponse response = Chat.ChatMessageResponse.newBuilder()
                        .setSender("Minecraft")
                        .setMessage("Received from " + sender + ": " + message + " (Terraria)")
                        .build();

                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                // Handle errors if needed
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
