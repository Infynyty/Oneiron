package de.kasyyy.oneiron.util.runnables;

import de.kasyyy.oneiron.custommobs.MobRegistry;
import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.util.Util;
import net.minecraft.server.v1_14_R1.EntityTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Objects;

public class SpawnEntity extends BukkitRunnable {

    private EntityTypes entity;
    private ArrayList<Location> locations;
    private OneironMob oneironMob;

    public SpawnEntity(EntityTypes entity, ArrayList<Location> locations, OneironMob oneironMob) {
        this.entity = entity;
        this.locations = locations;
        this.oneironMob = oneironMob;
    }

    @Override
    public void run() {
        for(Location location : locations) {
            if((location.getWorld().getNearbyEntities(location, 30, 30, 30, x -> x instanceof LivingEntity).size() < 8)
                    && location.getWorld().getNearbyEntities(location, 30, 30, 30, x -> x instanceof Player).size() > 0) {
                MobRegistry.spawnEntity(entity, location, oneironMob);
            }
        }
    }
}
