package de.kasyyy.oneiron.player.events;

import de.kasyyy.oneiron.items.armor.OneironArmor;
import de.kasyyy.oneiron.player.JoinEvent;
import de.kasyyy.oneiron.player.OneironPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class ChangeArmorEvent implements Listener {

    @EventHandler
    public void onChangeArmor(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (!(e.getClickedInventory().getType().equals(InventoryType.PLAYER))) return;
        if (e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            Player player = (Player) e.getWhoClicked();
            OneironPlayer oneironPlayer = JoinEvent.getAllOneironPlayers().get(player.getUniqueId());
            OneironArmor.changeArmor(oneironPlayer);
        }
    }
}
