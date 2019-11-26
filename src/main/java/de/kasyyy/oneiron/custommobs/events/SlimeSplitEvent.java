package de.kasyyy.oneiron.custommobs.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SlimeSplitEvent implements Listener {

    @EventHandler
    public void onSlimeSplit(org.bukkit.event.entity.SlimeSplitEvent e) {
        e.setCancelled(true);
    }
}
