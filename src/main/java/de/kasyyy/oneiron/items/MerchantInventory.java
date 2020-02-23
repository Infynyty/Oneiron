package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class MerchantInventory {
    private Inventory inventory;
    private int slotCounter = 18;

    /**
     * Creates a custom merchant inventory
     *
     * @param name
     */
    public MerchantInventory(String name) {
        inventory = Bukkit.createInventory(null, 9*5, name);
        for(int i = 0; i < 9; i++) {
            inventory.setItem(i, Util.crItem(Material.BLACK_STAINED_GLASS_PANE, 1, "", null));
        }
    }

    /**
     * Adds items to the inventory
     *
     * @param addedItem
     */
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
