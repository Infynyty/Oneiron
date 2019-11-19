package de.kasyyy.oneiron.custommobs.mobs;

import net.minecraft.server.v1_14_R1.*;
import org.bukkit.ChatColor;

public class WeakZombie extends EntityZombie {
    public WeakZombie(EntityTypes<? extends EntityZombie> entitytypes, World world) {
        super(EntityTypes.ZOMBIE, world);
        this.setCustomName(new ChatMessage(ChatColor.RED + "Weak Zombie"));
        this.setCustomNameVisible(true);
        this.setHealth(200);
    }
}
