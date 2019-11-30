package de.kasyyy.oneiron.custommobs.events;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OMDamagedByOM implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(!(e.getDamager().hasMetadata(Util.ID)) && (!(e.getEntity().hasMetadata(Util.ID)))) return;

        OneironMob damaged = OneironMob.getOneironMobs().get(e.getDamager().getMetadata(Util.ID).get(0).asInt());
        OneironMob attacker = OneironMob.getOneironMobs().get(e.getDamager().getMetadata(Util.ID).get(0).asInt());
        if(damaged.getName().equalsIgnoreCase(attacker.getName())) return;
        damaged.damageEntity(attacker.getDamage(), attacker.getEntity());
    }
}
