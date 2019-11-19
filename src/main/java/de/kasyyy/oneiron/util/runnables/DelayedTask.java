package de.kasyyy.oneiron.util.runnables;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class DelayedTask extends BukkitRunnable {
    private Player p;
    private HashMap<UUID, Integer> comboPoints;
    private int stop = 2; //How often the Runnable is executed before stopping

    public DelayedTask(Player p, HashMap<UUID, Integer> comboPoints) {
        this.p = p;
        this.comboPoints = comboPoints;
    }

    @Override
    public void run() {
        stop--;
        if(stop <= 0) {
            comboPoints.put(p.getUniqueId(), 1);
            this.cancel();
        }

    }
}
