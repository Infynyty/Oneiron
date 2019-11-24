package de.kasyyy.oneiron.util.runnables;

import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class HealthRegeneration extends BukkitRunnable {

    private OneironPlayer oneironPlayer;
    private int addedHealth;
    private ArrayList<UUID> playerRegenerating;

    public HealthRegeneration(OneironPlayer oneironPlayer, int addedHealth, ArrayList<UUID> playerRegenerating) {
        this.oneironPlayer = oneironPlayer;
        this.addedHealth = addedHealth;
        this.playerRegenerating = playerRegenerating;
        playerRegenerating.add(oneironPlayer.getUuid());
    }

    @Override
    public void run() {
        if (Bukkit.getPlayer(oneironPlayer.getUuid()) != null) {

            Player player = Bukkit.getPlayer(oneironPlayer.getUuid());
            float percent = (float) oneironPlayer.getHealth() / (float) oneironPlayer.getMaxHealth();

            if (oneironPlayer.getHealth() + addedHealth >= oneironPlayer.getMaxHealth()) {
                player.setHealth(Math.round(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue()));
                oneironPlayer.heal(addedHealth);
                player.sendMessage(Util.getDebug() + "You have been healed fully!");

                playerRegenerating.remove(oneironPlayer.getUuid());
                cancel();
            } else {
                oneironPlayer.heal(addedHealth);
                player.setHealth(Math.round(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue() * percent));
                player.sendMessage(Util.getDebug() + "You have been healed! Your health: " + oneironPlayer.getHealth());
            }
        } else {
            cancel();
        }
    }
}
