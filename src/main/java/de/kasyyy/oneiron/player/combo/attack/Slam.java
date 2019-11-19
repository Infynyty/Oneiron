package de.kasyyy.oneiron.player.combo.attack;

import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.Race;
import de.kasyyy.oneiron.util.Util;
import de.kasyyy.oneiron.util.runnables.SlamDamage;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Slam extends Attack {

    public Slam(int damage, int range, int manaCost, String name, Particle particle, Race.Races races) {
        super(damage, range, manaCost, name, particle, races);
    }

    @Override
    public void attack(Player p) {

        Vector vector = p.getLocation().getDirection();
        vector.multiply(3.5F);
        vector.setY(0);
        p.setVelocity(vector);
        p.sendMessage(Util.getDebug() + "Set velocity");
        p.sendMessage(Util.getDebug() + "Your speed: " + p.getWalkSpeed());
        new SlamDamage(p, damage, particle).runTaskTimer(Oneiron.getInstance(), 0, 2);
    }
}