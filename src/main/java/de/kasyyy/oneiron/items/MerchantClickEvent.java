package de.kasyyy.oneiron.items;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;


public class MerchantClickEvent implements Listener {

    @EventHandler
    public void onClickMerchant(InventoryOpenEvent e) {
        if(!(e.getInventory().getType().equals(InventoryType.MERCHANT))) return;
        e.setCancelled(true);
        Player p = (Player) e.getPlayer();
        OneironCurrency.openExchangeInv(p);
    }
}
