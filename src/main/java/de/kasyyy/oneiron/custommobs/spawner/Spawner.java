package de.kasyyy.oneiron.custommobs.spawner;

import de.kasyyy.oneiron.custommobs.MobRegistry;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.util.Util;
import de.kasyyy.oneiron.util.runnables.SpawnEntity;
import net.minecraft.server.v1_14_R1.EntityTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;

public class Spawner {
    private Location location;
    private EntityTypes entity;
    private ArrayList<Location> locationArrayList = new ArrayList<>();

    private Oneiron instance = Oneiron.getInstance();
    private static int amount = 0;

    public Spawner(Location location, EntityTypes entity) {
        this.location = location;
        this.entity = entity;
        instance.getConfig().set("Spawner." + amount + ".X", location.getX());
        instance.getConfig().set("Spawner." + amount + ".Y", location.getY());
        instance.getConfig().set("Spawner." + amount + ".Z", location.getZ());
        instance.getConfig().set("Spawner." + amount + ".Mob", MobRegistry.getAllEntities().inverse().get(entity));
        instance.saveConfig();
    }

    public void spawnMobs() {
        org.bukkit.Location loc1, loc2, loc3, loc4;
        loc1 = new Location(location.getWorld(), location.getX() + 3, location.getY(), location.getZ());
        loc2 = new Location(location.getWorld(), location.getX() - 3, location.getY(), location.getZ());
        loc3 = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ() + 3);
        loc4 = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ() - 3);

        locationArrayList.add(loc1);
        locationArrayList.add(loc2);
        locationArrayList.add(loc3);
        locationArrayList.add(loc4);

        Bukkit.broadcastMessage(Util.getDebug() + "Created all four locations!: " + loc1 + loc2);

        for(Location locInUse : locationArrayList) {
            while (locInUse.getBlock().getType() != Material.AIR) {
                locInUse.add(0, 1, 0);
            }
        }
        new SpawnEntity(entity, locationArrayList).runTaskTimer(instance, 0, 20*10);
    }


    public static void setAmount(int amount) {
        Spawner.amount = amount;
    }
}
