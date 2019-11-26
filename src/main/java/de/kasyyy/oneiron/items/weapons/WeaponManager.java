package de.kasyyy.oneiron.items.weapons;

import de.kasyyy.oneiron.items.OneironItem;
import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

public class WeaponManager {

    public static OneironItem weakStaff;
    public static OneironWeapon greatStaff;
    private WeaponManager() {}

    public static void loadWeapons() {
        Bukkit.getConsoleSender().sendMessage(Util.getPrefix() + "Loaded all Oneiron weapons");
        weakStaff = new OneironWeapon(10, Races.MAGE, true, true,
                Util.crItem(Material.STICK, 1, ChatColor.GREEN + "Weak Staff",
                        new ArrayList<String>() {{
                            add(ChatColor.DARK_PURPLE + "A weak staff");
                        }}), 50, 10);
        greatStaff = new OneironWeapon(10, Races.MAGE, true, true,
                Util.crItem(Material.BLAZE_ROD, 1, ChatColor.DARK_AQUA + "Great Staff",
                        new ArrayList<String>() {{
                            add(ChatColor.DARK_PURPLE + "A staff of unheard magic power");
                        }}), 10, 100);

    }

}
