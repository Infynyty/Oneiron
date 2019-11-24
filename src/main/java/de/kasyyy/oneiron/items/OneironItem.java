package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.player.Race;
import de.kasyyy.oneiron.player.Races;
import org.bukkit.inventory.ItemStack;

public abstract class OneironItem {
    private int value;
    private Races race;
    private boolean dropable;
    private boolean sellable;
    private ItemStack itemStack;

    public OneironItem(int value, Races race, boolean dropable, boolean sellable, ItemStack itemStack) {
        this.value = value;
        this.race = race;
        this.dropable = dropable;
        this.sellable = sellable;
        this.itemStack = itemStack;

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
}
