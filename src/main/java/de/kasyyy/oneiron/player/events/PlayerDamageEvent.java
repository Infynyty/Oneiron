package de.kasyyy.oneiron.player.events;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.player.JoinEvent;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageEvent implements Listener {
    //Todo: Put Regeneration into oneiron player


    //Oneiron players can only be damaged through the oneironplayer method TODO

    @EventHandler
    public void onPlayerDamaged(EntityDamageByEntityEvent e) {
        if(JoinEvent.getAllOneironPlayers().containsKey(e.getEntity().getUniqueId())) {
            if(!e.getDamager().hasMetadata(Util.ID)) return;
            e.setDamage(0);
            OneironPlayer oneironPlayer = JoinEvent.getAllOneironPlayers().get(e.getEntity().getUniqueId());
            OneironMob oneironMob = OneironMob.getOneironMobs().get(e.getDamager().getMetadata(Util.ID).get(0).asInt());
            if(oneironPlayer.isInvincible()) e.setCancelled(true);
            oneironPlayer.damagePlayer(oneironMob.getDamage());

        }
    }
}
