package de.kasyyy.oneiron.items.weapons;

import de.kasyyy.oneiron.items.OneironItem;
import de.kasyyy.oneiron.player.Race;
import de.kasyyy.oneiron.player.Races;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class OneironWeapon extends OneironItem {
    private int value;
    private Race race;
    private boolean dropable;
    private boolean sellable;
    private ItemStack itemStack;
    private final int damage;
    private static HashMap<OneironWeapon, Races> raceSpecificList = new HashMap<>();
    private static HashMap<ItemStack, OneironWeapon> OWFromIS = new HashMap<>(); //OW = OneironWeapon; IS = ItemStack

    public OneironWeapon(int value, Races race, boolean dropable, boolean sellable, ItemStack itemStack, int dropChance, int damage) {
        super(value, race, dropable, sellable, itemStack, dropChance);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(new ArrayList<String>(){{add(ChatColor.GRAY + "Damage: " + damage); addAll(Objects.requireNonNull(itemStack.getItemMeta().getLore()));}});
        itemStack.setItemMeta(itemMeta);
        ItemMeta shopItemMeta = super.getShopItemStack().getItemMeta();
        shopItemMeta.setLore(itemStack.getItemMeta().getLore());
        super.getShopItemStack().setItemMeta(shopItemMeta);
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
