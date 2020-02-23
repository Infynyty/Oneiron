package de.kasyyy.oneiron.items.weapons;

import de.kasyyy.oneiron.items.OneironItem;
import de.kasyyy.oneiron.items.Rarity;
import de.kasyyy.oneiron.player.Races;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class OneironWeapon extends OneironItem {
    private int value;
    private Races race;
    private boolean dropable;
    private boolean sellable;
    private ItemStack itemStack;
    private final int damage;
    private static HashMap<OneironWeapon, Races> raceSpecificList = new HashMap<>();
    private static HashMap<String, OneironWeapon> oWFromName = new HashMap<>();

    //Get an Oneiron weapon from an itemstack ingame
    private static HashMap<ItemStack, OneironWeapon> OWFromIS = new HashMap<>(); //OW = OneironWeapon; IS = ItemStack


    /**
     * Creates an OneironWeapon
     *
     * @param value      The value in screws
     * @param race       The race for which the weapon is designed
     * @param itemStack
     * @param dropChance The chance that the item is dropped; from 1(lowest) to 1000(highest)
     * @param damage
     */
    public OneironWeapon(int value, Races race, ItemStack itemStack, int dropChance, int damage, Rarity rarity) {
        super(value, itemStack, dropChance, rarity);
        this.race = race;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(new ArrayList<String>(){{add(ChatColor.GRAY + "Damage: " + damage); addAll(Objects.requireNonNull(itemStack.getItemMeta().getLore()));}});
        itemStack.setItemMeta(itemMeta);
        ItemMeta shopItemMeta = super.getShopItemStack().getItemMeta();
        shopItemMeta.setLore(itemStack.getItemMeta().getLore());
        super.getShopItemStack().setItemMeta(shopItemMeta);
        this.damage = damage;
        raceSpecificList.put(this, race);
        OWFromIS.put(itemStack, this);
        oWFromName.put(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()), this);
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

    public static HashMap<String, OneironWeapon> getoWFromName() {
        return oWFromName;
    }
}
