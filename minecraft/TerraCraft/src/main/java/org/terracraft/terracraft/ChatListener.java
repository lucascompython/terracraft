package org.terracraft.terracraft;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    private final ChatService chatService;

    public ChatListener(ChatService chatService) {
        this.chatService = chatService;
        System.out.println("ChatListener created");
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        String playerName = event.getPlayer().getName();
        String messageContent = ((TextComponent) event.message()).content();

        System.out.println("MENSAGEM ENVIADA");
        chatService.sendChatMessageToTerraria(playerName, messageContent);
    }
}