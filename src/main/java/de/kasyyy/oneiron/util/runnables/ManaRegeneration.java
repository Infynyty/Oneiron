package de.kasyyy.oneiron.util.runnables;

import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class ManaRegeneration extends BukkitRunnable {
    private OneironPlayer oneironPlayer;
    private int addedMana;
    private ArrayList<UUID> playerRegenerating = new ArrayList<>();
    private Player player;
    private boolean setCancelled;

    public ManaRegeneration(OneironPlayer oneironPlayer, int addedMana, ArrayList<UUID> playerRegenerating) {

        this.player = Bukkit.getPlayer(oneironPlayer.getUuid());
        this.oneironPlayer = oneironPlayer;
        this.addedMana = addedMana;
        this.playerRegenerating = playerRegenerating;
        playerRegenerating.add(oneironPlayer.getUuid());
    }

    @Override
    public void run() {

        if (Bukkit.getPlayer(oneironPlayer.getUuid()) != null) {

            Player player = Bukkit.getPlayer(oneironPlayer.getUuid());
            float percent = (float) oneironPlayer.getMana() / (float) oneironPlayer.getMaxMana();

            if (oneironPlayer.getMana() + addedMana >= oneironPlayer.getMaxMana()) {
                player.setFoodLevel(20);
                oneironPlayer.addMana(addedMana);
                playerRegenerating.remove(oneironPlayer.getUuid());
                cancel();
            } else {
                oneironPlayer.addMana(addedMana);
                player.setFoodLevel(Math.round(20 * percent));
            }
        } else {
            cancel();
        }
    }
}
