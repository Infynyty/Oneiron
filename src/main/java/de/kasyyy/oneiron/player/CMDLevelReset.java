package de.kasyyy.oneiron.player;

import de.kasyyy.oneiron.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDLevelReset implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        OneironPlayer oneironPlayer = JoinEvent.getAllOneironPlayers().get(((Player) commandSender).getUniqueId());
        oneironPlayer.setLevel(1);
        oneironPlayer.setXp(1);
        oneironPlayer.saveToConfig();
        commandSender.sendMessage(Util.getPrefix() + "Your level and xp have been reset!");
        return true;
    }
}
