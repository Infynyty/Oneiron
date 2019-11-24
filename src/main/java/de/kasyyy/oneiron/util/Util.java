package de.kasyyy.oneiron.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class Util {
    private final static String PREFIX = ChatColor.AQUA + "[Oneiron]:  ";
    private final static String DEBUG = ChatColor.RED + "[Debug]" + PREFIX;
    private final static String ERR_RELOAD = PREFIX + ChatColor.RED + "Using </rl> will cause errors using this plugin";

    public final static String ID = "ID";

    private Util() {}

    public static String getPrefix() {
        return PREFIX;
    }

    public static String getDebug() {
        return DEBUG;
    }

    public static String getErrReload() {
        return ERR_RELOAD;
    }

    public static ItemStack crItem(Material material, int amount, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * Damages a {@link LivingEntity}
     * @param entity The living entity to deal damage to
     * @param damage The amount of damage
     */
    public static void damageLivingEntity(LivingEntity entity, int damage) {
        if((entity).getHealth() - damage <= 0) {
            ( entity).setHealth(0);
        } else {
            entity.setHealth(entity.getHealth() - damage);
            entity.damage(1);
            Bukkit.broadcastMessage(Util.getDebug() + "Health: " + entity.getHealth());
        }

    }
}

