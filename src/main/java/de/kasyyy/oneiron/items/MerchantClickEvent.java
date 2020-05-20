package de.kasyyy.oneiron.items;

import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import de.kasyyy.oneiron.items.weapons.WeaponManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;


public class MerchantClickEvent implements Listener {
    private MerchantInventory merchantInventory = new MerchantInventory(ChatColor.GOLD + "Weapons");
    private static WeaponManager weaponManager = WeaponManager.getInstance();
    private String name = weaponManager.getShepherdsStaff().getItemStack().getItemMeta().getDisplayName();
    {
        merchantInventory.addItem(weaponManager.getShepherdsStaff());
    }

    @EventHandler
    public void onClickMerchant(InventoryOpenEvent e) {
        if(!(e.getInventory().getType().equals(InventoryType.MERCHANT))) return;
        e.setCancelled(true);
        Player p = (Player) e.getPlayer();
        p.openInventory(merchantInventory.getInventory());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!(e.getWhoClicked().getOpenInventory().getTopInventory().equals(merchantInventory.getInventory()))) return;
        e.setCancelled(true);
        if(e.getCurrentItem() == null) return;
        if(!(e.getCurrentItem().hasItemMeta())) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName().contains(name)) {
            ItemExchange.buyItem((Player) e.getWhoClicked(), weaponManager.getShepherdsStaff().getItemStack(), weaponManager.getShepherdsStaff().getValue());
        }
    }
}
