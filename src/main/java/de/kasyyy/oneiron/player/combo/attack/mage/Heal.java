package de.kasyyy.oneiron.player.combo.attack.mage;

import de.kasyyy.oneiron.player.JoinListener;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.player.combo.attack.Attack;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Heal extends Attack {
    public Heal(double damagePercent, int range, int manaCost, String name, Particle particle, Races races) {
        super(damagePercent, range, manaCost, name, particle, races);
    }

    @Override
    protected void attack(Player p) {
        OneironPlayer oneironPlayer = JoinListener.getAllOneironPlayers().get(p.getUniqueId());
        oneironPlayer.heal(Math.round(oneironPlayer.getMaxHealth() / 8));
        p.getLocation().getWorld().spawnParticle(particle, p.getLocation(), 10);
    }
}
