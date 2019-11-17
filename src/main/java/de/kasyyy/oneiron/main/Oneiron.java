package de.kasyyy.oneiron.main;

import de.kasyyy.oneiron.player.JoinEvent;
import de.kasyyy.oneiron.player.Race;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Oneiron extends JavaPlugin {

    private static Oneiron instance = null;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getConsoleSender().sendMessage(Util.getPrefix() + "Oneiron enabled!");

        this.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        this.getServer().getPluginManager().registerEvents(new Race(), this);
        setupConfig();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static Oneiron getInstance() {
        return instance;
    }
    public void setupConfig() {
        this.getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
