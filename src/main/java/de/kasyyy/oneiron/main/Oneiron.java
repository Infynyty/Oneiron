package de.kasyyy.oneiron.main;

import de.kasyyy.oneiron.custommobs.CMDcspawn;
import de.kasyyy.oneiron.custommobs.MobRegistry;
import de.kasyyy.oneiron.custommobs.OneironMobManager;
import de.kasyyy.oneiron.custommobs.events.JockeySpawnListener;
import de.kasyyy.oneiron.custommobs.events.OMDamagedByOMListener;
import de.kasyyy.oneiron.custommobs.events.OneironMobDeathListener;
import de.kasyyy.oneiron.custommobs.events.SlimeSplitListener;
import de.kasyyy.oneiron.custommobs.spawner.CMDcspawner;
import de.kasyyy.oneiron.custommobs.spawner.CMDspawnerdebug;
import de.kasyyy.oneiron.custommobs.spawner.DebugBlockBreakEvent;
import de.kasyyy.oneiron.custommobs.spawner.Spawner;
import de.kasyyy.oneiron.items.CMDoneironItems;
import de.kasyyy.oneiron.items.ExchangeListener;
import de.kasyyy.oneiron.items.MerchantClickEvent;
import de.kasyyy.oneiron.items.armor.ArmorManager;
import de.kasyyy.oneiron.items.weapons.WeaponManager;
import de.kasyyy.oneiron.player.*;
import de.kasyyy.oneiron.player.combo.AddRemovePlayerFromCPEvent;
import de.kasyyy.oneiron.player.combo.ComboManager;
import de.kasyyy.oneiron.player.combo.attack.Attack;
import de.kasyyy.oneiron.player.events.*;
import de.kasyyy.oneiron.player.events.changeArmor.ArmorListener;
import de.kasyyy.oneiron.player.events.changeArmor.ChangeArmorListener;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Oneiron extends JavaPlugin {

    private static Oneiron instance = null;


    @Override
    public void onLoad() {
        MobRegistry.registerAllMobs();
        WeaponManager.getInstance();
        ArmorManager.getInstance();
    }

    private Logger logger;
    @Override
    public void onEnable() {
        instance = this;
        logger = this.getLogger();
        Bukkit.getConsoleSender().sendMessage(Util.getPrefix() + "Oneiron enabled!");

        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new ArmorListener(null), this);
        this.getServer().getPluginManager().registerEvents(new Race(), this);
        this.getServer().getPluginManager().registerEvents(new ComboManager(), this);
        this.getServer().getPluginManager().registerEvents(new AddRemovePlayerFromCPEvent(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new FallDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new HungerChangeListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDamagedHungerListener(), this);
        this.getServer().getPluginManager().registerEvents(new OneironMobDeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerRegenerationListener(), this);
        this.getServer().getPluginManager().registerEvents(new SlimeSplitListener(), this);
        this.getServer().getPluginManager().registerEvents(new OMDamagedByOMListener(), this);
        this.getServer().getPluginManager().registerEvents(new DebugBlockBreakEvent(), this);
        this.getServer().getPluginManager().registerEvents(new MerchantClickEvent(), this);
        this.getServer().getPluginManager().registerEvents(new ExchangeListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerAttackListener(), this);
        this.getServer().getPluginManager().registerEvents(new JockeySpawnListener(), this);
        this.getServer().getPluginManager().registerEvents(new ChangeArmorListener(), this);

        this.getCommand("cspawner").setExecutor(new CMDcspawner());
        this.getCommand("cspawn").setExecutor(new CMDcspawn());
        this.getCommand("oneironitems").setExecutor(new CMDoneironItems());
        this.getCommand("setrace").setExecutor(new CMDSetRace());
        this.getCommand("lvlreset").setExecutor(new CMDLevelReset());
        this.getCommand("spawnerdebug").setExecutor(new CMDspawnerdebug());
        setupConfig();
        setUpSQL();
        Spawner.reloadSpawner();

        OneironMobManager.loadOneironMobs();

        //Creates an Oneiron player after a reload
        //TODO: Add sql support
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM OneironPlayer WHERE uuid = ?"
        )) {
//            Array array = connection.createArrayOf("VARCHAR(36)", Bukkit.getOnlinePlayers().toArray());
//            preparedStatement.setArray(1, array);
            for (Player p : Bukkit.getOnlinePlayers()) {
                preparedStatement.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    OneironPlayer oneironPlayer = new OneironPlayer(UUID.fromString(resultSet.getString("uuid")));
                    Bukkit.getPlayer(UUID.fromString(resultSet.getString("uuid"))).sendMessage(Util.getDebug() + "Successfully loaded player!");
                    JoinListener.getAllOneironPlayers().put(oneironPlayer.getUuid(), oneironPlayer);
                    if (oneironPlayer.getClasses().equals(Races.NONE)) {
                        Bukkit.getPlayer(UUID.fromString(resultSet.getString("uuid"))).sendMessage(Util.getDebug() + "Please choose a class!");
                        Bukkit.getPlayer(UUID.fromString(resultSet.getString("uuid"))).openInventory(Race.getChooseInv());
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        for(Player p : Bukkit.getOnlinePlayers()) {
//            OneironPlayer oneironPlayer = null;
//            if(instance.getConfig().contains(p.getUniqueId().toString())) {
//                oneironPlayer = new OneironPlayer(p.getUniqueId());
//                p.sendMessage(Util.getDebug() + "Succesfully loaded player from config");
//            }
//            if(oneironPlayer == null) {
//                p.kickPlayer(Util.getErrReload());
//            }
//            JoinEvent.getAllOneironPlayers().put(oneironPlayer.getUuid(), oneironPlayer);
//            if(oneironPlayer.getClasses().equals(Races.NONE)) {
//                p.sendMessage(Util.getDebug() + "Please choose a class!");
//                p.openInventory(Race.getChooseInv());
//            }
//        }

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
        //Disables debug mode if the server is reloaded
        if(CMDspawnerdebug.isDebug()) CMDspawnerdebug.setDebugBlocks();

        //Saves all oneiron players during a reload
        for(Player p : Bukkit.getOnlinePlayers()) {
            if (JoinListener.getAllOneironPlayers().containsKey(p.getUniqueId())) {
                OneironPlayer oneironPlayer = JoinListener.getAllOneironPlayers().get(p.getUniqueId());
                oneironPlayer.saveToConfig();
                Bukkit.getConsoleSender().sendMessage(Util.getDebug() + "Saved Player to config");
                JoinListener.getAllOneironPlayers().remove(p.getUniqueId());
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
        this.getConfig().addDefault("SQL.IP", 0);
        this.getConfig().addDefault("SQL.User", "null");
        this.getConfig().addDefault("SQL.Password", "null");
        this.getConfig().addDefault("SQL.Port", "null");
        for(int i = 0; i < 20; i++) {
            this.getConfig().addDefault("Level." + i, 100 + 2*i);
        }
        saveConfig();
        logger.log(Level.INFO, "Config has been set up");
    }

    //Creates a connection to a mysql database, if all data has been entered into the config
    public Connection getConnection() throws SQLException {
        if(this.getConfig().getString("SQL.IP") == null ||
            this.getConfig().getString("SQL.Port") == null ||
            this.getConfig().getString("SQL.User") == null) {
            this.logger.log(Level.SEVERE, "No SQL connection defined, stopping plugin!");
            Bukkit.getServer().getConsoleSender().sendMessage(Util.getPrefix() + ChatColor.RED + "No SQL connection defined, stopping plugin!");
            this.getServer().getPluginManager().disablePlugin(this);
            return null;
        }
        try {
            String url = "jdbc:mysql://" + this.getConfig().getString("SQL.IP") + ":" +
                    this.getConfig().getString("SQL.Port") + "/?useSSL=false&allowPublicKeyRetrieval=true";
            String user = this.getConfig().getString("SQL.User");
            String password = this.getConfig().getString("SQL.Password");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS Oneiron");
            connection.setCatalog("Oneiron");
            return connection;
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(Util.getPrefix() + ChatColor.RED + "Connection to database failed, stopping plugin!" +
                    "\nPlease check your login data and confirm that the database is running");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        return null;
    }

    //Creates tables for the players and the spawners
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
