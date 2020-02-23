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

    public static void changeArmor(OneironPlayer oneironPlayer) {
        Player player = Bukkit.getPlayer(oneironPlayer.getUuid());
        int i = 0;
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            OneironArmor oneironArmor = OneironArmor.getoAFromIS().get(itemStack);
            if (oneironArmor != null) {
                //Removes the effect of old armor
                if (oneironPlayer.getArmor()[i] != null) {
                    player.sendMessage(Util.getDebug() + "Old damage: " + oneironPlayer.getDamage());
                    oneironPlayer.setMaxHealth(oneironPlayer.getMaxHealth() - oneironPlayer.getArmor()[i].getAddedHealth());
                    oneironPlayer.setMaxMana(oneironPlayer.getMaxMana() - oneironPlayer.getArmor()[i].getAddedMana());
                    oneironPlayer.setDefense(oneironPlayer.getDefense() - oneironPlayer.getArmor()[i].getAddedDefense());
                    oneironPlayer.setSpeed(oneironPlayer.getSpeed() - oneironPlayer.getArmor()[i].getAddedSpeed());
                    oneironPlayer.setDamage(oneironPlayer.getDamage() - oneironPlayer.getArmor()[i].getAddedDamage());
                }

                //Adds the effect of new armor pieces
                oneironPlayer.setMaxHealth(oneironPlayer.getMaxHealth() + oneironArmor.getAddedHealth());
                oneironPlayer.setMaxMana(oneironPlayer.getMaxMana() + oneironArmor.getAddedMana());
                oneironPlayer.setDefense(oneironPlayer.getDefense() + oneironArmor.getAddedDefense());
                oneironPlayer.setSpeed(oneironPlayer.getSpeed() + oneironArmor.getAddedSpeed());
                oneironPlayer.setDamage(oneironPlayer.getDamage() + oneironArmor.getAddedDamage());
                oneironPlayer.getArmor()[i] = oneironArmor;
                player.sendMessage(Util.getDebug() + "Added new armor piece and changed stats");
                player.sendMessage(Util.getDebug() + "New damage: " + oneironPlayer.getDamage());
                player.sendMessage(Util.getDebug() + "Added damage: " + oneironArmor.getAddedDamage());
                i++;
            }
        }
    }

    public static void removeArmor(OneironPlayer oneironPlayer) {
        Player player = Bukkit.getPlayer(oneironPlayer.getUuid());
        int i = 0;
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (itemStack == null) return;
            OneironArmor oneironArmor = OneironArmor.getoAFromIS().get(itemStack);
            if (oneironArmor != null) {
                //Removes the effect of old armor
                oneironPlayer.setMaxHealth(oneironPlayer.getMaxHealth() - oneironPlayer.getArmor()[i].getAddedHealth());
                oneironPlayer.setMaxMana(oneironPlayer.getMaxMana() - oneironPlayer.getArmor()[i].getAddedMana());
                oneironPlayer.setDefense(oneironPlayer.getDefense() - oneironPlayer.getArmor()[i].getAddedDefense());
                oneironPlayer.setSpeed(oneironPlayer.getSpeed() - oneironPlayer.getArmor()[i].getAddedSpeed());
                oneironPlayer.setDamage(oneironPlayer.getDamage() - oneironPlayer.getArmor()[i].getAddedDamage());
                i++;
                player.sendMessage(Util.getDebug() + "Removed all armor effects");
            }
        }
    }
}





