package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class OneironCurrency extends OneironItem {
    public OneironCurrency(int value, Races race, boolean dropable, boolean sellable, ItemStack itemStack, int dropChance) {
        super(value, race, dropable, sellable, itemStack, dropChance);
    }

    private static ArrayList<String> loreScrew = new ArrayList<String>(Arrays.asList("Old", "Rusty"));
    private static ItemStack screw = Util.crItem(Material.GOLD_NUGGET, 1, ChatColor.DARK_GRAY + "Screw", loreScrew);
    public final static OneironCurrency SCREW = new OneironCurrency(1, Races.NONE, true, true, screw, 300);

    private static ArrayList<String> loreScrapMetal = new ArrayList<String>(Arrays.asList("Old", "Rusty"));
    private static ItemStack scrapMetal = Util.crItem(Material.IRON_INGOT, 1, ChatColor.GRAY + "Scrap Metal", loreScrapMetal);
    public final static OneironCurrency SCRAP_METAL = new OneironCurrency(64, Races.NONE, true, true, scrapMetal, 25);

    private static ItemStack scToMt = Util.crItem(Material.IRON_INGOT, 1, "Screw to ScrapMetal", null);

    public static ItemStack getMtToSc() {
        return mtToSc;
    }

    private static ItemStack mtToSc = Util.crItem(Material.GOLD_NUGGET, 1, "ScrapMetal to Screw", null);

    private static Inventory exchangeInv = Bukkit.createInventory(null, 9, "Exchange");

    public static void openExchangeInv(Player p) {
        exchangeInv.setItem(0, scToMt);
        exchangeInv.setItem(1 ,mtToSc);
        p.openInventory(exchangeInv);
    }
}
