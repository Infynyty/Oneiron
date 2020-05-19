package de.kasyyy.oneiron.custommobs.events;

import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.event.entity.EntityPathfindEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OneironMobTargetListener implements Listener {

    @EventHandler
    public void onTarget(EntityPathfindEvent e) {
        Pathfinder mob = (Pathfinder) e.getEntity();
    }
}
