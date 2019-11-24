package de.kasyyy.oneiron.player.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class PlayerDamagedHungerEvent implements Listener {

    @EventHandler
    public void onHungerDamage(EntityDamageEvent e) {

        if(!(e.getCause().equals(EntityDamageEvent.DamageCause.STARVATION))) return;
        e.setCancelled(true);
    }
}
