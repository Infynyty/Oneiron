package de.kasyyy.oneiron.player.combo.attack;

import de.kasyyy.oneiron.items.weapons.OneironWeapon;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.JoinEvent;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.util.runnables.SlamDamage;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Slam extends Attack {


    public Slam(double damagePercent, int range, int manaCost, String name, Particle particle, Races races) {
        super(damagePercent, range, manaCost, name, particle, races);
    }

    @Override
    public void attack(Player p) {

        Vector vector = p.getLocation().getDirection();
        vector.multiply(3.5F);
        vector.setY(0);
        p.setVelocity(vector);

        OneironPlayer oneironPlayer = JoinEvent.getAllOneironPlayers().get(p.getUniqueId());
        oneironPlayer.setInvincible(true);
        new SlamDamage(p,(int) Math.round(OneironWeapon.getOWFromIS().get(p.getItemInHand()).getDamage() * damagePercent),
                particle).runTaskTimer(Oneiron.getInstance(), 0, 2);
        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_CAT_HISS, 5.0F, 5.0F);
    }
}