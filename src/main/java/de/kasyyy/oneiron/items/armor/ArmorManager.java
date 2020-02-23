package de.kasyyy.oneiron.items.armor;


import de.kasyyy.oneiron.items.OneironItem;
import de.kasyyy.oneiron.items.Rarity;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.logging.Level;

public class ArmorManager {
    private static ArmorManager armorManager = null;

    public final OneironItem leatherJacket;

    private ArmorManager() {
        leatherJacket = new OneironArmor(5, Util.crItem(Material.LEATHER_CHESTPLATE, 1, "Leather Jacket", null)
                , 1000, Rarity.COMMON, 50, 50, 1, 30, 100);
        Bukkit.getLogger().log(Level.INFO, "Loaded all Oneiron armor pieces!");
    }

    public static ArmorManager getInstance() {
        return armorManager == null ? armorManager = new ArmorManager() : armorManager;
    }
}
