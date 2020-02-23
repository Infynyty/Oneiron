package de.kasyyy.oneiron.player.combo.attack;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.items.weapons.OneironWeapon;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.JoinEvent;
import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Rage extends Attack {

    public Rage(double damagePercent, int range, int manaCost, String name, Particle particle, Races races) {
        super(damagePercent, range, manaCost, name, particle, races);
    }

    @Override
    protected void attack(Player p) {
        p.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, p.getLocation(), 1);
        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);

        ArrayList<Entity> allEntities = new ArrayList<>();

        p.getLocation().getWorld().getNearbyEntities(p.getLocation(), range, range, range).stream().filter(x -> x.hasMetadata(Util.ID)).forEach(entity -> allEntities.add(entity));

        for(Entity entity : allEntities) {
            //If the player changes the weapon before the runnable has run, a NullpointerEx occurs
            final int damage = (int) (OneironWeapon.getOWFromIS().get(p.getItemInHand()).getDamage() * damagePercent);
            entity.setVelocity(new Vector(entity.getVelocity().getX(), 1.5, entity.getVelocity().getZ()));
            OneironMob oneironMob = OneironMob.getOneironMobs().get(entity.getMetadata(Util.ID).get(0).asInt());
            new BukkitRunnable() {

                @Override
                public void run() {


                    entity.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation(), 1);
                    if (oneironMob != null)
                        Rage.super.attackOM(JoinEvent.getAllOneironPlayers().get(p.getUniqueId()), oneironMob);
                }
            }.runTaskLater(Oneiron.getInstance(), 20);
        }

    }
}
