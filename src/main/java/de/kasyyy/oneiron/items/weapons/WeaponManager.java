package de.kasyyy.oneiron.items.weapons;

import de.kasyyy.oneiron.items.OneironItem;
import de.kasyyy.oneiron.items.Rarity;
import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

/**
 * All weapons should be registered in this class
 */
public class WeaponManager {

    private static WeaponManager weaponManager = null;

    private final OneironItem weakStaff;
    private final OneironItem greatStaff;
    private final OneironItem birchWoodStaff;
    private final OneironItem shepherdsStaff;
    private final OneironItem wandOfSleep;


    private WeaponManager() {
        weakStaff = new OneironWeapon(10, Races.MAGE, Util.crItem(Material.STICK, 1, "Weak Staff",
                new ArrayList<String>() {{
                    add(ChatColor.GRAY + "A weak staff");
                }}), 0, 10, Rarity.COMMON);
        birchWoodStaff = new OneironWeapon(10, Races.MAGE, Util.crItem(Material.STICK, 1, "Birchwood Staff",
                new ArrayList<String>() {{
                    add(ChatColor.GRAY + "A short staff made from birchwood");
                }}), 30, 20, Rarity.UNCOMMON);
        shepherdsStaff = new OneironWeapon(50, Races.MAGE, Util.crItem(Material.STICK, 1, "Shepherd's Staff",
                new ArrayList<String>() {{
                    add(ChatColor.GRAY + "Seems like it was used by a shepherd");
                    add(ChatColor.GRAY + "a long time ago");
                }}), 20, 50, Rarity.RARE);
        greatStaff = new OneironWeapon(10, Races.MAGE, Util.crItem(Material.BLAZE_ROD, 1, "Great Staff",
                new ArrayList<String>() {{
                    add(ChatColor.GRAY + "A staff of unheard magic power");
                }}), 5, 100, Rarity.LEGENDARY);
        wandOfSleep = new OneironWeapon(1000, Races.MAGE, Util.crItem(Material.BLAZE_ROD, 1, "Wand of sleep",
                new ArrayList<String>() {{
                    add(ChatColor.GRAY + "The wand of the ancient god of sleep");
                }}),
                1, 1000, Rarity.MYTHIC);
        Bukkit.getConsoleSender().sendMessage(Util.getPrefix() + "Loaded all Oneiron weapons");
    }

    public static WeaponManager getInstance() {
        return weaponManager == null ? weaponManager = new WeaponManager() : weaponManager;
    }

    public OneironItem getWeakStaff() {
        return weakStaff;
    }

    public OneironItem getGreatStaff() {
        return greatStaff;
    }

    public OneironItem getBirchWoodStaff() {
        return birchWoodStaff;
    }

    public OneironItem getShepherdsStaff() {
        return shepherdsStaff;
    }
}
