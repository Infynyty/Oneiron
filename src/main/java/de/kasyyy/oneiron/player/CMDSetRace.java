package de.kasyyy.oneiron.player;

import de.kasyyy.oneiron.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CMDSetRace implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("setrace")) {
            if(!(commandSender instanceof Player)) return true;
            OneironPlayer oneironPlayer = JoinEvent.getAllOneironPlayers().get(((Player) commandSender).getUniqueId());
            if(strings.length == 1) {
                try {
                    Races races = Races.valueOf(strings[0]);
                    oneironPlayer.setRace(races);
                } catch (IllegalArgumentException ex) {
                    commandSender.sendMessage(Util.getPrefix() + "This race doesn't exist!");
                    return true;
                }

            }
        }
        return true;
    }
}
