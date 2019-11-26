package de.kasyyy.oneiron.custommobs.mobs;

import net.minecraft.server.v1_14_R1.*;
import org.bukkit.ChatColor;

public class ForestSpider extends EntitySpider {
    public final static String NAME = ChatColor.GREEN + "Forest Spider";

    public ForestSpider(EntityTypes<? extends EntitySpider> entitytypes, World world) {
        super(EntityTypes.SPIDER, world);
        this.setCustomName(new ChatMessage(NAME));
        this.setCustomNameVisible(true);

        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<>(this, WeakZombie.class, false));
    }
}
