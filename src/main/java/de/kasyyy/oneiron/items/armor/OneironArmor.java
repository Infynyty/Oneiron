package de.kasyyy.oneiron.items.armor;

import de.kasyyy.oneiron.items.Rarity;
import de.kasyyy.oneiron.items.StatAlteringItem;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class OneironArmor extends StatAlteringItem {

    private static HashMap<ItemStack, OneironArmor> oAFromIS = new HashMap<>();

    public OneironArmor(int value, ItemStack itemStack, int dropChance, Rarity rarity, int addedHealth, int addedMana, int addedSpeed, int addedDefense, int addedDamage) {
        super(value, itemStack, dropChance, rarity, addedHealth, addedMana, addedSpeed, addedDefense, addedDamage);
        oAFromIS.putIfAbsent(itemStack, this);
    }

    public static HashMap<ItemStack, OneironArmor> getoAFromIS() {
        return oAFromIS;
    }


}





