package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.items.weapons.OneironWeapon;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class CMDoneironItems implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        StringBuilder name = new StringBuilder();
        boolean first = true;
        for (String string : strings) {
            if (first) {
                name.append(string);
                first = false;
            } else {
                name.append(" ").append(string);
            }

            commandSender.sendMessage(Util.getDebug() + "The name: " + name.toString());
        }
        if (OneironWeapon.getoWFromName().containsKey(name.toString())) {
            ItemStack itemStack = OneironWeapon.getoWFromName().get(name.toString()).getItemStack();
            ((Player) commandSender).getInventory().addItem(itemStack);
            commandSender.sendMessage(Util.getPrefix() + "Added weapon!");
        } else {
            commandSender.sendMessage(Util.getPrefix() + "This weapon doesn't exist!\n These are all existing weapons: ");
            for (String weapon : OneironWeapon.getoWFromName().keySet()) {
                commandSender.sendMessage(Util.getPrefix() + weapon);
            }
        }

        return true;
    }
}
