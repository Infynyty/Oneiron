package de.kasyyy.oneiron.custommobs.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;


public class JockeySpawnEvent implements Listener {

    @EventHandler
    public void onSpawnJockey(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.JOCKEY) e.setCancelled(true);
    }
}
