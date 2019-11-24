package de.kasyyy.oneiron.items.weapons;

import de.kasyyy.oneiron.items.OneironItem;
import de.kasyyy.oneiron.player.Race;
import de.kasyyy.oneiron.player.Races;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class OneironWeapon extends OneironItem {
    private int value;
    private Race race;
    private boolean dropable;
    private boolean sellable;
    private ItemStack itemStack;
    private final int damage;
    private static HashMap<OneironWeapon, Races> raceSpecificList = new HashMap<>();
    private static HashMap<ItemStack, OneironWeapon> OWFromIS = new HashMap<>(); //OW = OneironWeapon; IS = ItemStack

    public OneironWeapon(int value, Races race, boolean dropable, boolean sellable, ItemStack itemStack, int damage) {
        super(value, race, dropable, sellable, itemStack);
        this.damage = damage;
        raceSpecificList.put(this, race);
        OWFromIS.put(itemStack, this);
    }

    public static HashMap<OneironWeapon, Races> getRaceSpecificList() {
        return raceSpecificList;
    }

    public int getDamage() {
        return damage;
    }

    public static HashMap<ItemStack, OneironWeapon> getOWFromIS() {
        return OWFromIS;
    }
}
