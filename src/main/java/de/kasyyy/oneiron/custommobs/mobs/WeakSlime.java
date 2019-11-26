package de.kasyyy.oneiron.custommobs.mobs;

import net.minecraft.server.v1_14_R1.*;
import org.bukkit.ChatColor;

public class WeakSlime extends EntitySlime {
    public static final String name = ChatColor.GREEN + "Weak Slime";

    public WeakSlime(EntityTypes<? extends EntitySlime> entitytypes, World world) {
        super(EntityTypes.SLIME, world);
        this.setCustomName(new ChatMessage(name));
        this.setCustomNameVisible(true);
        this.setSize(4, true);

        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<>(this, WeakZombie.class, false));
    }
}
