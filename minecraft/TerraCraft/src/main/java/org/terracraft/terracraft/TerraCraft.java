package org.terracraft.terracraft;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class TerraCraft extends JavaPlugin {

    public Logger logger = getLogger();

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("TerraCraft has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("TerraCraft has been disabled!");
    }
}