package de.kasyyy.oneiron.player;

import de.kasyyy.oneiron.items.weapons.WeaponManager;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Race implements Listener {

    //The inventory which is opened up after joining if a player has the Race NONE
    private static Inventory chooseInv = Bukkit.createInventory(null, 9, ChatColor.GREEN + "Choose your class");

    private static ItemStack archer = Util.crItem(Material.BOW, 1, ChatColor.DARK_GREEN + "Archer", new ArrayList<String>()
    {{add(ChatColor.DARK_PURPLE  + "A quick runner, regarded as one"); add(ChatColor.DARK_PURPLE + "of the best archers in the kingdom");}});
    private static ItemStack mage = Util.crItem(Material.BLAZE_ROD, 1, ChatColor.DARK_PURPLE + "Mage", new ArrayList<String>()
    {{add(ChatColor.BLUE + "A royal mage has always been an impressive"); add(ChatColor.BLUE + "sight. Many great feasts have been completed"); add(ChatColor.BLUE + "by these extraordinary warriors");}});

    static {
        chooseInv.setItem(2, archer);
        chooseInv.setItem(5, mage);
    }


    public static Inventory getChooseInv() {
        return chooseInv;
    }

    //Opens the setup inventory
    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if(e.getWhoClicked().getOpenInventory().getTopInventory().equals(chooseInv)) {
            e.setCancelled(true);
            OneironPlayer oneironPlayer = JoinEvent.getAllOneironPlayers().get(e.getWhoClicked().getUniqueId());
            if(e.getCurrentItem().equals(archer)) {
                oneironPlayer.setRace(Races.ARCHER);
                e.getWhoClicked().getOpenInventory().close();
            }
            else if(e.getCurrentItem().equals(mage)) {
                oneironPlayer.setRace(Races.MAGE);
                oneironPlayer.addMana(oneironPlayer.getMaxMana());
                Bukkit.getPlayer(oneironPlayer.getUuid()).getInventory().addItem(WeaponManager.getInstance().getWeakStaff().getItemStack());
                e.getWhoClicked().getOpenInventory().close();
            }
        }
    }

    //Keeps the player from exiting the inventory without having chosen a race
    @EventHandler
    public void closeInv(InventoryCloseEvent e) {
        if(e.getInventory().equals(chooseInv)) {
            OneironPlayer oneironPlayer = JoinEvent.getAllOneironPlayers().get(e.getPlayer().getUniqueId());
            if(oneironPlayer.getClasses().equals(Races.NONE)) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Oneiron.getInstance(), () -> {
                    e.getPlayer().openInventory(chooseInv);
                    e.getPlayer().sendMessage(Util.getPrefix() + "Please choose a class");
                }, 20);

            }
        }
    }
}


