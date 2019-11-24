package de.kasyyy.oneiron.player.events;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.JoinEvent;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.util.Util;
import de.kasyyy.oneiron.util.runnables.HealthRegeneration;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerDamageEvent implements Listener {
    //Todo: Put Regeneration into oneiron player
    private static ArrayList<UUID> playerRegenerating = new ArrayList<>();

    //Oneiron players can only be damaged through the oneironplayer method TODO

    @EventHandler
    public void onPlayerDamaged(EntityDamageByEntityEvent e) {
        if(JoinEvent.getAllOneironPlayers().containsKey(e.getEntity().getUniqueId())) {
            if(!e.getDamager().hasMetadata(Util.ID)) return;
            e.setDamage(0);
            OneironPlayer oneironPlayer = JoinEvent.getAllOneironPlayers().get(e.getEntity().getUniqueId());
            OneironMob oneironMob = OneironMob.getOneironMobs().get(e.getDamager().getMetadata(Util.ID).get(0).asInt());

            float percent = (float) oneironPlayer.getHealth() / (float) oneironPlayer.getMaxHealth();
            oneironPlayer.damage(oneironMob.getDamage());


            Bukkit.getPlayer(oneironPlayer.getUuid()).setHealth(Math.round(20 * percent));
            Bukkit.getPlayer(oneironPlayer.getUuid()).sendMessage(Util.getDebug() + "You have been damaged: Percent: " + percent);

            if(!playerRegenerating.contains(oneironPlayer.getUuid())) {
                new HealthRegeneration(oneironPlayer, 2, playerRegenerating).runTaskTimer(Oneiron.getInstance(), 20 * 3, 20);
            }
        }
    }

    public static ArrayList<UUID> getPlayerRegenerating() {
        return playerRegenerating;
    }
}
