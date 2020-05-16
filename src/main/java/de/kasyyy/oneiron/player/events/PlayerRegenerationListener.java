package de.kasyyy.oneiron.player.events;

import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class PlayerRegenerationListener implements Listener {

    @EventHandler
    public void onPlayerRegen(EntityRegainHealthEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        e.setCancelled(true);
    }
}
