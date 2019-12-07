package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.items.weapons.OneironWeapon;
import de.kasyyy.oneiron.items.weapons.WeaponManager;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class MerchantInventory {
    private Inventory inventory;
    private int slotCounter = 18;


    public MerchantInventory(String name) {
        inventory = Bukkit.createInventory(null, 9*5, name);
        for(int i = 0; i < 9; i++) {
            inventory.setItem(i, Util.crItem(Material.BLACK_STAINED_GLASS_PANE, 1, "", null));
        }
    }

    public void addItem(OneironItem... addedItem) {
        for(OneironItem oneironItem : addedItem) {
            if(slotCounter > inventory.getSize()) return;

            inventory.setItem(slotCounter, oneironItem.getShopItemStack());
            slotCounter++;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}
