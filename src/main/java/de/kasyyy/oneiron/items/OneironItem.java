package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class OneironItem {
    private int value, dropChance;
    private Races race;
    private boolean dropable;
    private boolean sellable;
    private final ItemStack itemStack;
    private final ItemStack shopItemStack;
    private static HashMap<ItemStack, OneironItem> OIFromIS = new HashMap<>(); //OW = OneironItem; IS = ItemStack

    public OneironItem(int value, Races race, boolean dropable, boolean sellable, ItemStack itemStack, int dropChance) {
        this.value = value;
        this.race = race;
        this.dropable = dropable;
        this.sellable = sellable;
        this.itemStack = itemStack;
        this.dropChance = dropChance;
        shopItemStack = Util.crItem(itemStack.getType(), itemStack.getAmount(), itemStack.getItemMeta().getDisplayName() + ChatColor.GREEN + " [" + value + "]", itemStack.getItemMeta().getLore());
        OIFromIS.put(itemStack, this);
    }

    public int getValue() {
        return value;
    }

    public Races getRace() {
        return race;
    }

    public boolean isDropable() {
        return dropable;
    }

    public boolean isSellable() {
        return sellable;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getDropChance() {
        return dropChance;
    }

    public ItemStack getShopItemStack() {
        return shopItemStack;
    }
}
