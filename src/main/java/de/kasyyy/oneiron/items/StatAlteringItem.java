package de.kasyyy.oneiron.items;

import org.bukkit.inventory.ItemStack;

public abstract class StatAlteringItem extends OneironItem {

    private int addedHealth, addedMana = 0;
    private int addedSpeed, addedDefense, addedDamage = 0; //in percent


    /**
     * Creates an item, which influences a players stats
     *
     * @param value        Value in screws
     * @param itemStack    The itemstack
     * @param dropChance   The dropchance from 1 (low) to 1000 (high)
     * @param rarity       The rarity
     * @param addedHealth  The health that gets added to the player
     * @param addedMana    The mana that gets added to the player
     * @param addedSpeed   Adds speed to the player (in percent)
     * @param addedDefense The defense that gets added to the player (in percent)
     * @param addedDamage  Adds damage to the player
     */
    public StatAlteringItem(int value, ItemStack itemStack, int dropChance, Rarity rarity, int addedHealth, int addedMana, int addedSpeed, int addedDefense, int addedDamage) {
        super(value, itemStack, dropChance, rarity);
        this.addedHealth = addedHealth;
        this.addedMana = addedMana;
        this.addedSpeed = addedSpeed;
        this.addedDefense = addedDefense;
        this.addedDamage = addedDamage;
    }

    public void setAddedDamage(int addedDamage) {
        this.addedDamage = addedDamage;
    }

    public void setAddedDefense(int addedDefense) {
        this.addedDefense = addedDefense;
    }

    public void setAddedHealth(int addedHealth) {
        this.addedHealth = addedHealth;
    }

    public int getAddedSpeed() {
        return addedSpeed;
    }

    public int getAddedMana() {
        return addedMana;
    }

    public int getAddedHealth() {
        return addedHealth;
    }

    public int getAddedDefense() {
        return addedDefense;
    }

    public int getAddedDamage() {
        return addedDamage;
    }
}
