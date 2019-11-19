package de.kasyyy.oneiron.custommobs.events;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class OneironMobDeathEvent implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if(e.getEntity().hasMetadata(Util.ID)) {
            Bukkit.broadcastMessage(Util.getDebug() + "OneironMob died!");
            OneironMob oneironMob = OneironMob.getOneironMobs().get(e.getEntity().getMetadata(Util.ID).get(0).asInt());
            e.setDroppedExp(0);
            e.getDrops().clear();
            if(oneironMob.getDrops() != null) {
                e.getDrops().addAll(oneironMob.getDrops());
            }
            OneironMob.getOneironMobs().remove(oneironMob.getId());
        }
    }
}
