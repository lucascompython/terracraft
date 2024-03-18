package org.terracraft.terracraft;

import io.grpc.stub.StreamObserver;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.terracraft.grpc.Chat;
import org.terracraft.grpc.ChatServiceGrpc;

import java.security.GeneralSecurityException;
import java.util.logging.Logger;

public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {

    private final Server server;
    private final Logger logger;
    private StreamObserver<Chat.ChatMessage> chatMessageStreamObserver;
    private final Encryption encryption;
    private final String token;

    public ChatService(Server server, Logger logger, Encryption encryption, String token) {
        this.server = server;
        this.logger = logger;
        this.encryption = encryption;
        this.token = token;
    }

    @Override
    public StreamObserver<Chat.ChatMessage> chat(final StreamObserver<Chat.ChatMessage> responseObserver) {
        this.chatMessageStreamObserver = responseObserver;

        return new StreamObserver<Chat.ChatMessage>() {
            @Override
            public void onNext(Chat.ChatMessage request) {

                try {
                    String decryptedToken = encryption.Decrypt(request.getToken());
                    if (!decryptedToken.equals(token)) {
                        logger.warning("Invalid token");
                        this.onError(new RuntimeException("Invalid token"));
                        return;
                    }
                } catch (GeneralSecurityException e) {
                    this.onError(new RuntimeException("Invalid token"));
                    return;
                }

                if (request.getComesFromServer()) {
                    responseObserver.onNext(request);
                    return;
                }

                try {
                    String message = encryption.Decrypt(request.getMessage());
                    String sender = encryption.Decrypt(request.getSender());

                    server.broadcast(Component.text("[Terraria] <" + sender + ">" + " " + message));
                } catch (GeneralSecurityException e) {
                    this.onError(new RuntimeException("Can't decrypt data, invalid token or data was tampered with."));
                }
            }

            @Override
            public void onError(Throwable t) {
                logger.warning("Error occurred: " + t.getMessage());
                this.onCompleted();
            }

            @Override
            public void onCompleted() {
                if (chatMessageStreamObserver != null) {
                    responseObserver.onCompleted();
                    chatMessageStreamObserver = null;
                    logger.info("Chat stream has been closed.");
                }
            }
        };
    }

    public void sendChatMessageToTerraria(String sender, String message) {
        if (chatMessageStreamObserver == null) {
            logger.warning("Chat message stream observer is null");
            return;
        }
        try {

            Chat.ChatMessage request = Chat.ChatMessage.newBuilder()
                    .setMessage(encryption.Encrypt(message))
                    .setSender(encryption.Encrypt(sender))
                    .setToken(encryption.Encrypt(token))
                    .setComesFromServer(true)
                    .build();
            chat(chatMessageStreamObserver).onNext(request);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

    }
}
