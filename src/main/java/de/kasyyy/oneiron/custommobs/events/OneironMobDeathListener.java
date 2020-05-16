package de.kasyyy.oneiron.custommobs.events;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class OneironMobDeathListener implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if(e.getEntity().hasMetadata(Util.ID)) {
            OneironMob oneironMob = OneironMob.getOneironMobs().get(e.getEntity().getMetadata(Util.ID).get(0).asInt());
            e.setDroppedExp(0);
            e.getDrops().clear();
            OneironMob.getOneironMobs().remove(oneironMob.getId());
        }
    }
}
