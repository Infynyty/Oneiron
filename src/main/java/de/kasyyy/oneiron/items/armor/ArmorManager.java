package de.kasyyy.oneiron.items.armor;


import de.kasyyy.oneiron.items.OneironItem;
import de.kasyyy.oneiron.items.Rarity;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.logging.Level;

public class ArmorManager {
    private static ArmorManager armorManager = null;

    public final OneironItem leatherJacket;
    public final OneironItem shinyPants;

    private ArmorManager() {

        leatherJacket = new OneironArmor(5, Util.crItem(Material.LEATHER_CHESTPLATE, 1, "Leather Jacket", null)
                , 1000, Rarity.COMMON, 50, 50, 1, 30, 100);
        Bukkit.getLogger().log(Level.INFO, "Loaded all Oneiron armor pieces!");
        shinyPants = new OneironArmor(10, Util.crItem(Material.DIAMOND_LEGGINGS, 1, "Shiny Pants", null),
                1000, Rarity.RARE, 50, 50, 1, 20, 200);
    }

    public static ArmorManager getInstance(){
        return armorManager == null ? armorManager = new ArmorManager() : armorManager;
    }
}
