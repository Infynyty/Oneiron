package de.kasyyy.oneiron.player.combo.attack;

import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.util.Util;
import de.kasyyy.oneiron.util.runnables.ManaRegeneration;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Attack {
    protected final double damagePercent;
    protected final int range;
    protected final int manaCost;
    protected final String name;
    protected Particle particle;
    protected Effect effect;
    protected final Races races;
    private static final int MANA_REGENERATION = 10;

    private static final int MANA_DELAY = 20 * 3;
    private static final int MANA_PERIOD = 20;

    private static ArrayList<UUID> playerRegenerating = new ArrayList<>();

    /**
     * To create a custom attack, extend this class and overwrite the
     * {@link #attack(Player)} method.
     *
     * @param damagePercent The percentage of the damage of the weapon used, that will be dealt.
     * @param range         If an attack has a specific range, it can be defined here.
     * @param manaCost      The mana that will be used by this attack.
     * @param name          The attacks name.
     * @param particle      The particle used. This can be changed for higher levels of the same attack.
     * @param races         The race which should be able to use this attack.
     */
    public Attack(double damagePercent, int range, int manaCost, String name, final Particle particle, Races races) {
        this.damagePercent = damagePercent;
        this.range = range;
        this.manaCost = manaCost;
        this.name = name;
        this.particle = particle;
        this.races = races;
    }

    /**
     * To create a custom attack, extend this class and overwrite the
     * {@link #attack(Player)} method.
     *
     * @param damagePercent The percentage of the damage of the weapon used, that will be dealt.
     * @param range         If an attack has a specific range, it can be defined here.
     * @param manaCost      The mana that will be used by this attack.
     * @param name          The attacks name.
     * @param effect        The effect used. This can be changed for higher levels of the same attack.
     * @param races         The race which should be able to use this attack.
     */
    public Attack(double damagePercent, int range, int manaCost, String name, final Effect effect, Races races) {
        this.damagePercent = damagePercent;
        this.range = range;
        this.manaCost = manaCost;
        this.name = name;
        this.effect = effect;
        this.races = races;
    }

    /**
     * Override this method to implement custom logic.
     * Do not use this to call an attack.
     * Always get the attack damage before starting a scheduler.
     * @param p Player who uses the attack
     */
    protected abstract void attack(Player p);

    /**
     * Use this method to call an attack
     * @param oneironPlayer The player using the attack
     */
    public void attackUsed(final OneironPlayer oneironPlayer) {
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

        if (!playerRegenerating.contains(oneironPlayer.getUuid())) {
            new ManaRegeneration(oneironPlayer, MANA_REGENERATION,
                    playerRegenerating).runTaskTimer(Oneiron.getInstance(), MANA_DELAY, MANA_PERIOD);
        }
    }

    public static ArrayList<UUID> getPlayerRegenerating() {
        return playerRegenerating;
    }
}
