package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.main.Oneiron;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ExchangeEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!e.getClickedInventory().equals(OneironCurrency.getExchangeInv())) return;
        e.setCancelled(true);
        if(e.getCurrentItem().equals(OneironCurrency.getMtToSc())) {
            ItemExchange.exchangeItems((Player) e.getWhoClicked(), OneironCurrency.SCRAP_METAL.getItemStack(), 1,
                    OneironCurrency.SCREW.getItemStack(), 64);
            return;
        }
        if(e.getCurrentItem().equals(OneironCurrency.getScToMt())) {
            ItemExchange.exchangeItems((Player) e.getWhoClicked(), OneironCurrency.SCREW.getItemStack(), 64,
                    OneironCurrency.SCRAP_METAL.getItemStack(), 1);
            return;
        }

    }
}
