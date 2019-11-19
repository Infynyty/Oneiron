package de.kasyyy.oneiron.player.events;

import de.kasyyy.oneiron.player.JoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageEvent implements Listener {

    //Oneiron players can only be damaged through the oneironplayer method TODO
    @EventHandler
    public void onPlayerDamaged(EntityDamageByEntityEvent e) {
        if(JoinEvent.getAllPlayers().containsKey(e.getEntity().getUniqueId())) {
            e.setDamage(0);
        }
    }
}
