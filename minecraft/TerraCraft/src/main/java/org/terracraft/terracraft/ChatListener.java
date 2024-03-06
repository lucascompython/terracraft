package org.terracraft.terracraft;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

public class ChatListener implements Listener {

    private final Logger logger;

    public ChatListener(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        String messageContent = ((TextComponent) event.message()).content();
        logger.info("Player " + event.getPlayer().getName() + " said: " + messageContent);
    }
}
