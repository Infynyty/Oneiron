package de.kasyyy.oneiron.player.combo;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AddRemovePlayerFromCPEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        ComboManager.getComboPoints().put(e.getPlayer().getUniqueId(), 1);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        ComboManager.getComboPoints().remove(e.getPlayer().getUniqueId());
    }
}
