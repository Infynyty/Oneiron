package de.kasyyy.oneiron.main;

import de.kasyyy.oneiron.custommobs.MobRegistry;
import de.kasyyy.oneiron.custommobs.events.OneironMobDeathEvent;
import de.kasyyy.oneiron.custommobs.spawner.CMDcspawner;
import de.kasyyy.oneiron.player.JoinEvent;
import de.kasyyy.oneiron.player.Race;
import de.kasyyy.oneiron.player.combo.ComboManager;
import de.kasyyy.oneiron.player.events.FallDamageEvent;
import de.kasyyy.oneiron.player.events.HungerChangeEvent;
import de.kasyyy.oneiron.player.events.PlayerDamageEvent;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Oneiron extends JavaPlugin {

    private static Oneiron instance = null;

    @Override
    public void onLoad() {
        MobRegistry.registerAllMobs();
    }

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getConsoleSender().sendMessage(Util.getPrefix() + "Oneiron enabled!");

        this.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        this.getServer().getPluginManager().registerEvents(new Race(), this);
        this.getServer().getPluginManager().registerEvents(new ComboManager(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDamageEvent(), this);
        this.getServer().getPluginManager().registerEvents(new FallDamageEvent(), this);
        this.getServer().getPluginManager().registerEvents(new HungerChangeEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OneironMobDeathEvent(), this);

        this.getCommand("cspawner").setExecutor(new CMDcspawner());
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
