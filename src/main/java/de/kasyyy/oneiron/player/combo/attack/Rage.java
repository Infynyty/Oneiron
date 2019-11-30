package de.kasyyy.oneiron.player.combo.attack;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.items.weapons.OneironWeapon;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.JoinEvent;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Rage extends Attack {

    public Rage(double damagePercent, int range, int manaCost, String name, Particle particle, Races races) {
        super(damagePercent, range, manaCost, name, particle, races);
    }

    @Override
    protected void attack(Player p) {
        p.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, p.getLocation(), 1);
        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
        for (Entity entity : p.getLocation().getWorld().getNearbyEntities(p.getLocation(), range, range, range)) {
            if(entity.equals(p)) continue;
            if (!(entity instanceof LivingEntity)) continue;

            if (!(entity.hasMetadata(Util.ID))) continue;
            //If the player changes the weapon before the runnable has run, a NullpointerEx occurs
            final int damage = (int) (OneironWeapon.getOWFromIS().get(p.getItemInHand()).getDamage() * damagePercent);
            OneironMob oneironMob = OneironMob.getOneironMobs().get(entity.getMetadata(Util.ID).get(0).asInt());


            entity.setVelocity(new Vector(entity.getVelocity().getX(), 1.5, entity.getVelocity().getZ()));
            new BukkitRunnable() {

                @Override
                public void run() {

                    entity.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation(), 1);
                    if(oneironMob != null) oneironMob.damageEntity(damage, p);
                }
            }.runTaskLater(Oneiron.getInstance(), 20);


        }
    }
}
