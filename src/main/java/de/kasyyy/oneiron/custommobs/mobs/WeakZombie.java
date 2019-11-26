package de.kasyyy.oneiron.custommobs.mobs;

import net.minecraft.server.v1_14_R1.ChatMessage;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EntityZombie;
import net.minecraft.server.v1_14_R1.World;
import org.bukkit.ChatColor;

public class WeakZombie extends EntityZombie {
    public static final String WEAK_ZOMBIE_NAME = ChatColor.RED + "Weak Zombie";

    public WeakZombie(EntityTypes<? extends EntityZombie> entitytypes, World world) {
        super(EntityTypes.ZOMBIE, world);
        this.setCustomName(new ChatMessage(WEAK_ZOMBIE_NAME));
        this.setCustomNameVisible(true);
        this.setHealth(200);
        this.setBaby(false);
    }
}
