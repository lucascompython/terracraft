package org.terracraft.terracraft;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

public class ChatListener implements Listener {

    private final Logger logger;
    private final ChatService chatService;

    public ChatListener(Logger logger, ChatService chatService) {
        this.logger = logger;
        this.chatService = chatService;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        String playerName = event.getPlayer().getName();
        String messageContent = ((TextComponent) event.message()).content();

        logger.info("Player " + playerName + " said: " + messageContent);

        chatService.sendChatMessageToTerraria(playerName, messageContent);
    }
}