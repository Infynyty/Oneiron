package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class OneironCurrency extends OneironItem {
    private OneironCurrency(int value, ItemStack itemStack, int dropChance) {
        super(value, itemStack, dropChance, Rarity.COMMON);
    }

    private static HashMap<String, Inventory> oneironInventories = new HashMap<>();

    private static ArrayList<String> loreScrew = new ArrayList<String>(Arrays.asList("Old", "Rusty"));
    private static ItemStack screw = Util.crItem(Material.GOLD_NUGGET, 1, ChatColor.DARK_GRAY + "Screw", loreScrew);
    public final static OneironItem SCREW = new OneironCurrency(1, screw, 300);

    private static ArrayList<String> loreScrapMetal = new ArrayList<String>(Arrays.asList("Old", "Rusty"));
    private static ItemStack scrapMetal = Util.crItem(Material.IRON_INGOT, 1, ChatColor.GRAY + "Scrap Metal", loreScrapMetal);
    public final static OneironItem SCRAP_METAL = new OneironCurrency(64, scrapMetal, 205);

    private static ItemStack scToMt = Util.crItem(Material.IRON_INGOT, 1, "Screw to ScrapMetal", null);

    public static ItemStack getMtToSc() {
        return mtToSc;
    }

    private static ItemStack mtToSc = Util.crItem(Material.GOLD_NUGGET, 1, "ScrapMetal to Screw", null);

    public final static String EXCHANGE_NAME = "Exchange";
    private static Inventory exchangeInv = Bukkit.createInventory(null, 9, EXCHANGE_NAME);

    static {
        oneironInventories.putIfAbsent(EXCHANGE_NAME, exchangeInv);
    }
    public static void openExchangeInv(Player p) {
        exchangeInv.setItem(0, scToMt);
        exchangeInv.setItem(1 ,mtToSc);
        p.openInventory(exchangeInv);

    }

    public static HashMap<String, Inventory> getOneironInventories() {
        return oneironInventories;
    }

    public static Inventory getExchangeInv() {
        return exchangeInv;
    }

    public static ItemStack getScToMt() {
        return scToMt;
    }
}
