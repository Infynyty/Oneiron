package de.kasyyy.oneiron.custommobs.spawner;

import de.kasyyy.oneiron.custommobs.MobRegistry;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.util.Util;
import de.kasyyy.oneiron.util.runnables.SpawnEntity;
import net.minecraft.server.v1_14_R1.EntityTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

public class Spawner {

    private static Oneiron instance = Oneiron.getInstance();
    private static int amount = 0;

    public Spawner(Location location, EntityTypes entity) {
        amount++;
        try(Connection connection = Oneiron.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO OneironSpawner VALUES (?, ?, ?, ?, ?)"
        )) {
            preparedStatement.setString(1, location.getWorld().getName());
            preparedStatement.setInt(2, location.getBlockX());
            preparedStatement.setInt(3, location.getBlockY());
            preparedStatement.setInt(4, location.getBlockZ());
            preparedStatement.setString(5, MobRegistry.getAllEntities().inverse().get(entity));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*instance.getConfig().set("Spawner." + amount + ".World", location.getWorld().getName());
        instance.getConfig().set("Spawner." + amount + ".X", location.getX());
        instance.getConfig().set("Spawner." + amount + ".Y", location.getY());
        instance.getConfig().set("Spawner." + amount + ".Z", location.getZ());
        instance.getConfig().set("Spawner." + amount + ".Mob", MobRegistry.getAllEntities().inverse().get(entity));
        instance.getConfig().set("Spawner.Amount", amount);
        instance.saveConfig();*/
        spawnMobs(location, entity);

    }

    /**
     * Creates four Locations out of one, set in the shape of a square
     *
     * @param location The middle of the square
     * @return Returns an ArrayList with all four Locations
     */
    private static ArrayList<Location> createLocsFromLoc(Location location) {
        ArrayList<Location> locationArrayList = new ArrayList<>();

        org.bukkit.Location loc1, loc2, loc3, loc4;
        loc1 = new Location(location.getWorld(), location.getX() + 3, location.getY(), location.getZ());
        loc2 = new Location(location.getWorld(), location.getX() - 3, location.getY(), location.getZ());
        loc3 = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ() + 3);
        loc4 = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ() - 3);

        locationArrayList.add(loc1);
        locationArrayList.add(loc2);
        locationArrayList.add(loc3);
        locationArrayList.add(loc4);

        for(Location locInUse : locationArrayList) {
            while (locInUse.getBlock().getType() != Material.AIR) {
                locInUse.add(0, 1, 0);
            }
        }

        return locationArrayList;
    }

    /**
     * Spawns a custom entity at a given location
     * @param location
     * @param entity
     */
    private static void spawnMobs(Location location, EntityTypes entity) {
        ArrayList<Location> locationArrayList = createLocsFromLoc(location);
        new SpawnEntity(entity, locationArrayList, null).runTaskTimer(instance, 0, 20*10);
    }


    /**
     * Reloads all spawners from the config file
     */
    public static void reloadSpawner() {
        try(Connection connection = Oneiron.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM OneironSpawner"
        )) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return;
            do {
                String world = resultSet.getString("world");
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                int z = resultSet.getInt("z");
                EntityTypes entity = MobRegistry.getAllEntities().get(resultSet.getString("entity"));
                Location location = new Location(Bukkit.getWorld(world), x, y, z);
                spawnMobs(location, entity);
                Oneiron.getInstance().getLogger().log(Level.INFO, "Reloaded a spawner");
            } while (resultSet.next());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*Location location = new Location(Bukkit.getWorld(Oneiron.getInstance().getConfig().getString("Spawner." + i + ".World")),
                Oneiron.getInstance().getConfig().getDouble("Spawner." + i + ".X"),
                Oneiron.getInstance().getConfig().getDouble("Spawner." + i + ".Y"),
                Oneiron.getInstance().getConfig().getDouble("Spawner." + i + ".Z"));
        EntityTypes entity = MobRegistry.getAllEntities().get(Oneiron.getInstance().getConfig().getString("Spawner." + i + ".Mob"));
        spawnMobs(location, entity);
        Bukkit.getConsoleSender().sendMessage(Util.getDebug() + entity.a(((CraftWorld) location.getWorld()).getHandle()).getCustomName().g().getString());*/
    }


    public static void setAmount(int amount) {
        Spawner.amount = amount;
    }

    public static int getAmount() {
        return amount;
    }
}
