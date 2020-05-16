package de.kasyyy.oneiron.util.runnables;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.player.JoinListener;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class SlamDamage extends BukkitRunnable {
    private int i = 0;
    private Player p;
    private int damage;
    private Particle particle;

    public SlamDamage(Player p, int damage, Particle particle) {
        this.p = p;
        this.damage = damage;
        this.particle = particle;
    }

    @Override
    public void run() {
        if(i>5) {
            OneironPlayer oneironPlayer = JoinListener.getAllOneironPlayers().get(p.getUniqueId());
            oneironPlayer.setInvincible(false);
            cancel();
        }
        i++;
        Location location = p.getLocation();
        List<Entity> entities= p.getNearbyEntities(2, 2, 2);
        for(Entity entity : entities) {
            if(!(entity instanceof LivingEntity)) return;
            if(entity.hasMetadata(Util.ID)) {
                OneironMob oneironMob = OneironMob.getOneironMobs().get(entity.getMetadata(Util.ID).get(0).asInt());
                if(oneironMob != null) {
                    oneironMob.damageEntity(damage, p);
                    location.getWorld().spawnParticle(particle, location, 10);
                }
            }

        }
    }
}
