package de.kasyyy.oneiron.items.weapons;

import de.kasyyy.oneiron.items.OneironItem;
import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

public class WeaponManager {

    private static WeaponManager weaponManager = null;

    private final OneironItem weakStaff;
    private final OneironWeapon greatStaff;
    private final OneironItem birchWoodStaff;
    private final OneironItem shepherdsStaff;


    private WeaponManager() {
        weakStaff = new OneironWeapon(10, Races.MAGE, true, true,
                Util.crItem(Material.STICK, 1, ChatColor.GRAY + "Weak Staff",
                        new ArrayList<String>() {{
                            add(ChatColor.DARK_PURPLE + "A weak staff");
                        }}), 0, 10);
        birchWoodStaff = new OneironWeapon(10, Races.MAGE, true, true,
                Util.crItem(Material.STICK, 1, ChatColor.LIGHT_PURPLE + "Birchwood Staff",
                        new ArrayList<String>() {{
                            add(ChatColor.DARK_PURPLE + "A short staff made from birchwood");
                        }}), 30, 20);
        shepherdsStaff = new OneironWeapon(50, Races.MAGE, true, true,
                Util.crItem(Material.STICK, 1, ChatColor.YELLOW + "Shepherd's Staff",
                        new ArrayList<String>() {{
                            add(ChatColor.DARK_PURPLE + "Seems like it was used by a shepherd");
                            add(ChatColor.DARK_PURPLE + "a long time ago");
                        }}), 20, 50);
        greatStaff = new OneironWeapon(10, Races.MAGE, true, true,
                Util.crItem(Material.BLAZE_ROD, 1, ChatColor.DARK_AQUA + "Great Staff",
                        new ArrayList<String>() {{
                            add(ChatColor.DARK_PURPLE + "A staff of unheard magic power");
                        }}), 5, 100);
        Bukkit.getConsoleSender().sendMessage(Util.getPrefix() + "Loaded all Oneiron weapons");
    }

    public static WeaponManager getInstance() {
        if(weaponManager == null) {
            return new WeaponManager();
        } else {
            return weaponManager;
        }
//        return weaponManager == null ? weaponManager = new WeaponManager() : weaponManager;

    }

    public OneironItem getWeakStaff() {
        return weakStaff;
    }

    public OneironWeapon getGreatStaff() {
        return greatStaff;
    }

    public OneironItem getBirchWoodStaff() {
        return birchWoodStaff;
    }

    public OneironItem getShepherdsStaff() {
        return shepherdsStaff;
    }
}
