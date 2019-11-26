package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.player.Races;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class OneironItem {
    private int value, dropChance;
    private Races race;
    private boolean dropable;
    private boolean sellable;
    private ItemStack itemStack;
    private static HashMap<ItemStack, OneironItem> OIFromIS = new HashMap<>(); //OW = OneironItem; IS = ItemStack

    public OneironItem(int value, Races race, boolean dropable, boolean sellable, ItemStack itemStack, int dropChance) {
        this.value = value;
        this.race = race;
        this.dropable = dropable;
        this.sellable = sellable;
        this.itemStack = itemStack;
        this.dropChance = dropChance;
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
}
