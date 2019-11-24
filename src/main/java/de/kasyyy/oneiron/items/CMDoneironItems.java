package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.items.weapons.OneironWeapon;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMDoneironItems implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(Util.getDebug() + "All items: " + OneironWeapon.getOWFromIS());

        return true;
    }
}
