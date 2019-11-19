package de.kasyyy.oneiron.player.combo;

import de.kasyyy.oneiron.util.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class TESTINGComboItems {
    public static ArrayList<ItemStack> getClassItems() {
        return classItems;
    }

    private static ArrayList<ItemStack> classItems = new ArrayList<>();
    private static ItemStack sword = Util.crItem(Material.GOLDEN_SWORD, 1, "Short sword", null);

    public static ItemStack getSword() {
        return sword;
    }

    static {
        classItems.add(sword);
    }
}
