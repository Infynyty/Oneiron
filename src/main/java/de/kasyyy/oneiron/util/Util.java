package de.kasyyy.oneiron.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Util {
    private static String prefix = ChatColor.AQUA + "[Oneiron]:  ";
    private static String debug = ChatColor.RED + "[Debug]" + prefix;

    public static String getPrefix() {
        return prefix;
    }

    public static String getDebug() {
        return debug;
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
        if((entity).getHealth() - damage < 0) {
            ( entity).setHealth(0);
        } else {
            entity.setHealth(entity.getHealth() - damage);
        }

    }
}

