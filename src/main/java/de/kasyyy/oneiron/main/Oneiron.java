package de.kasyyy.oneiron.main;

import de.kasyyy.oneiron.custommobs.CMDcspawn;
import de.kasyyy.oneiron.custommobs.MobRegistry;
import de.kasyyy.oneiron.custommobs.OneironMobManager;
import de.kasyyy.oneiron.custommobs.events.OMDamagedByOM;
import de.kasyyy.oneiron.custommobs.events.OneironMobDeathEvent;
import de.kasyyy.oneiron.custommobs.events.SlimeSplitEvent;
import de.kasyyy.oneiron.custommobs.spawner.CMDcspawner;
import de.kasyyy.oneiron.custommobs.spawner.CMDspawnerdebug;
import de.kasyyy.oneiron.custommobs.spawner.DebugBlockBreakEvent;
import de.kasyyy.oneiron.custommobs.spawner.Spawner;
import de.kasyyy.oneiron.items.CMDoneironItems;
import de.kasyyy.oneiron.items.weapons.WeaponManager;
import de.kasyyy.oneiron.player.*;
import de.kasyyy.oneiron.player.combo.AddRemovePlayerFromCPEvent;
import de.kasyyy.oneiron.player.combo.ComboManager;
import de.kasyyy.oneiron.player.combo.attack.Attack;
import de.kasyyy.oneiron.player.events.*;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Oneiron extends JavaPlugin {

    private static Oneiron instance = null;

    @Override
    public void onLoad() {
        MobRegistry.registerAllMobs();
    }

    private Logger logger;
    @Override
    public void onEnable() {
        instance = this;
        logger = this.getLogger();
        Bukkit.getConsoleSender().sendMessage(Util.getPrefix() + "Oneiron enabled!");

        this.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        this.getServer().getPluginManager().registerEvents(new Race(), this);
        this.getServer().getPluginManager().registerEvents(new ComboManager(), this);
        this.getServer().getPluginManager().registerEvents(new AddRemovePlayerFromCPEvent(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDamageEvent(), this);
        this.getServer().getPluginManager().registerEvents(new FallDamageEvent(), this);
        this.getServer().getPluginManager().registerEvents(new HungerChangeEvent(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDamagedHungerEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OneironMobDeathEvent(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerRegenerationEvent(), this);
        this.getServer().getPluginManager().registerEvents(new SlimeSplitEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OMDamagedByOM(), this);
        this.getServer().getPluginManager().registerEvents(new DebugBlockBreakEvent(), this);

        this.getCommand("cspawner").setExecutor(new CMDcspawner());
        this.getCommand("cspawn").setExecutor(new CMDcspawn());
        this.getCommand("oneironitems").setExecutor(new CMDoneironItems());
        this.getCommand("setrace").setExecutor(new CMDSetRace());
        this.getCommand("lvlreset").setExecutor(new CMDLevelReset());
        this.getCommand("spawnerdebug").setExecutor(new CMDspawnerdebug());
        setupConfig();
        setUpSQL();
        Spawner.reloadSpawner();
        WeaponManager.loadWeapons();
        OneironMobManager.loadOneironMobs();

        //Creates an Oneiron player after a reload
        for(Player p : Bukkit.getOnlinePlayers()) {
            OneironPlayer oneironPlayer = null;
            if(instance.getConfig().contains(p.getUniqueId().toString())) {
                oneironPlayer = new OneironPlayer(p.getUniqueId());
                p.sendMessage(Util.getDebug() + "Succesfully loaded player from config");
            }
            if(oneironPlayer == null) {
                p.kickPlayer(Util.getErrReload());
            }
            JoinEvent.getAllOneironPlayers().put(oneironPlayer.getUuid(), oneironPlayer);
            if(oneironPlayer.getClasses().equals(Races.NONE)) {
                p.sendMessage(Util.getDebug() + "Please choose a class!");
                p.openInventory(Race.getChooseInv());
            }
        }

    }

    @Override
    public void onDisable() {
        //Removes all oneiron mobs from all worlds
        for(World w : Bukkit.getWorlds()) {
            for(Entity entity: w.getEntities()) {
                if (entity.hasMetadata(Util.ID)) {
                    entity.remove();
                }
            }
        }
        if(CMDspawnerdebug.isDebug()) CMDspawnerdebug.setDebugBlocks();

        //Saves all oneiron players during a reload
        for(Player p : Bukkit.getOnlinePlayers()) {
            if (JoinEvent.getAllOneironPlayers().containsKey(p.getUniqueId())) {
                OneironPlayer oneironPlayer = JoinEvent.getAllOneironPlayers().get(p.getUniqueId());
                oneironPlayer.saveToConfig();
                Bukkit.getConsoleSender().sendMessage(Util.getDebug() + "Saved Player to config");
                JoinEvent.getAllOneironPlayers().remove(p.getUniqueId());
            }
            Attack.getPlayerRegenerating().remove(p.getUniqueId());
            OneironPlayer.getPlayerRegenerating().remove(p.getUniqueId());
        }
        instance = null;
    }

    public static Oneiron getInstance() {
        return instance;
    }

    private void setupConfig() {
        this.getConfig().options().copyDefaults(true);
        this.getConfig().addDefault("Spawner.Amount", 0);
        this.getConfig().addDefault("SQL.IP", 0);
        this.getConfig().addDefault("SQL.User", null);
        this.getConfig().addDefault("SQL.Password", null);
        this.getConfig().addDefault("SQL.Port", null);
        for(int i = 0; i < 20; i++) {
            this.getConfig().addDefault("Level." + i, 100 + 2*i);
        }
        saveConfig();



        logger.log(Level.INFO, "Config has been set up");
    }

    //TODO: Create DB if it doesn't exist
    public Connection getConnection() throws SQLException {
        if(this.getConfig().getString("SQL.IP") == null ||
            this.getConfig().getString("SQL.Port") == null ||
            this.getConfig().getString("SQL.User") == null) {
            this.logger.log(Level.SEVERE, "No SQL connection defined, stopping plugin!");
            Bukkit.getServer().getConsoleSender().sendMessage(Util.getPrefix() + ChatColor.RED + "No SQL connection defined, stopping plugin!");
            this.getServer().getPluginManager().disablePlugin(this);
            return null;
        }
        String url = "jdbc:mysql://" + this.getConfig().getString("SQL.IP") + ":" +
                this.getConfig().getString("SQL.Port") + "/Oneiron?useSSL=false&allowPublicKeyRetrieval=true";
        String user = this.getConfig().getString("SQL.User");
        String password = this.getConfig().getString("SQL.Password");
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public void setUpSQL() {
        try(Connection connection = getConnection(); Statement createTable = connection.createStatement()) {
            createTable.executeUpdate("" +
                    "CREATE TABLE IF NOT EXISTS OneironPlayer " +
                    "(race VARCHAR(20), " +
                    "uuid VARCHAR(36), " +
                    "maxHealth SMALLINT, " +
                    "maxMana SMALLINT, " +
                    "level SMALLINT, " +
                    "xp INT)");
            createTable.executeUpdate("CREATE TABLE IF NOT EXISTS OneironSpawner (" +
                    "world VARCHAR(20), " +
                    "x INT, " +
                    "y INT, " +
                    "z INT, " +
                    "entity VARCHAR(60))");
            logger.log(Level.INFO, "Connected to Database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
