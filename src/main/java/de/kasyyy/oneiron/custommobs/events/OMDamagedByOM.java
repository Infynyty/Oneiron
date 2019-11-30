package de.kasyyy.oneiron.custommobs.events;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.logging.Level;

public class OMDamagedByOM implements Listener {

    //Handles the damage if one OneironMob attacks another
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager().hasMetadata(Util.ID)) && (!(e.getEntity().hasMetadata(Util.ID)))) return;
        if (e.getEntity().hasMetadata(Util.INVULNERABLE)) {
            e.setCancelled(true);
            return;
        }

        try {
            OneironMob damaged = OneironMob.getOneironMobs().get(e.getDamager().getMetadata(Util.ID).get(0).asInt());
            OneironMob attacker = OneironMob.getOneironMobs().get(e.getDamager().getMetadata(Util.ID).get(0).asInt());
            if (damaged.getName().equalsIgnoreCase(attacker.getName())) return;
            damaged.damageEntity(attacker.getDamage(), attacker.getEntity());

        } catch (IndexOutOfBoundsException ex) {
            Oneiron.getInstance().getLogger().log(Level.WARNING, "Could not find entity meta");
        }


        //Prevents mobs from damaging their own race
    }
}
