package org.terracraft.terracraft;

import io.grpc.stub.StreamObserver;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.terracraft.grpc.Chat;
import org.terracraft.grpc.ChatServiceGrpc;

import java.util.logging.Logger;

public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {

    private final Server server;
    private final Logger logger;
    private StreamObserver<Chat.ChatMessage> chatMessageStreamObserver;

    public ChatService(Server server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Override
    public StreamObserver<Chat.ChatMessage> chat(final StreamObserver<Chat.ChatMessage> responseObserver) {
        this.chatMessageStreamObserver = responseObserver;

        return new StreamObserver<Chat.ChatMessage>() {
            @Override
            public void onNext(Chat.ChatMessage request) {
                String sender = request.getSender();
                String message = request.getMessage();

                if (request.getComesFromServer()) {
                    responseObserver.onNext(request);
                    logger.info("Chat message from server: " + message);
                    return;
                }
                String formattedMessage = "[Terraria] <" + sender + ">" + " " + message;
                logger.info(formattedMessage);
                server.broadcast(Component.text(formattedMessage));
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

    public void sendChatMessageToTerraria(String sender, String message) {
        if (chatMessageStreamObserver == null) {
            logger.warning("Chat message stream observer is null");
            return;
        }
        Chat.ChatMessage request = Chat.ChatMessage.newBuilder()
                .setSender(sender)
                .setMessage(message)
                .setComesFromServer(true)
                .build();

        chat(chatMessageStreamObserver).onNext(request);
    }
}
