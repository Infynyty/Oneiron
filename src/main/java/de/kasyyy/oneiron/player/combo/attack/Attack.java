package de.kasyyy.oneiron.player.combo.attack;

import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.player.Race;
import de.kasyyy.oneiron.util.Util;
import de.kasyyy.oneiron.util.runnables.ManaRegeneration;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Attack {
    int damage;
    int range;
    int manaCost;
    String name;
    Particle particle;
    Effect effect;
    Race.Races races;
    private final int MANA_REGENERATION = 5;

    private static ArrayList<UUID> playerRegenerating = new ArrayList<>();


    public Attack(int damage, int range, int manaCost, String name, Particle particle, Race.Races races) {
        this.damage = damage;
        this.range = range;
        this.manaCost = manaCost;
        this.name = name;
        this.particle = particle;
        this.races = races;
    }

    public Attack(int damage, int range, int manaCost, String name, Effect effect, Race.Races races) {
        this.damage = damage;
        this.range = range;
        this.manaCost = manaCost;
        this.name = name;
        this.effect = effect;
        this.races = races;
    }

    /**
     * Override this method to implement custom logic
     * Do not use this to call an attack
     * @param p Player who uses the attack
     */
    protected abstract void attack(Player p);

    /**
     * Use this method to call an attack
     * @param oneironPlayer The player using the attack
     */
    public void attackUsed(OneironPlayer oneironPlayer) {
        Player player = Bukkit.getPlayer(oneironPlayer.getUuid());
        if(oneironPlayer.removeMana(manaCost)) {
            //Set hunger level
            float percent = oneironPlayer.getMana() / (float) oneironPlayer.getMaxMana();
            player.setFoodLevel(Math.round(20 * percent));

            attack(player);
        } else {
            player.sendMessage(Util.getDebug() + "You don't have enough mana!");
        }

        //Checks if the task is already running
        if(!playerRegenerating.contains(oneironPlayer.getUuid())) {
            new ManaRegeneration(oneironPlayer, MANA_REGENERATION, playerRegenerating).runTaskTimer(Oneiron.getInstance(), 20*3, 10);
        }
    }

    public static ArrayList<UUID> getPlayerRegenerating() {
        return playerRegenerating;
    }
}
