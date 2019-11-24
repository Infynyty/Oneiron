package de.kasyyy.oneiron.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDSetRace implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("setrace")) {
            if(!(commandSender instanceof Player)) return true;
            OneironPlayer oneironPlayer = JoinEvent.getAllOneironPlayers().get(((Player) commandSender).getUniqueId());
            if(strings.length == 1) {
                if(strings[0].equalsIgnoreCase("none")) {
                    oneironPlayer.setRace(Races.NONE);
                }
            }
        }
        return true;
    }
}
