package de.kasyyy.oneiron.custommobs;

import de.kasyyy.oneiron.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CMDcspawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(command.getName().equalsIgnoreCase("cspawn")) {
            if(!(commandSender instanceof Player)) return true;
            Player p = (Player) commandSender;
            if(strings.length == 0) {
                p.sendMessage(Util.getPrefix() + "Possible mobs:\n");
                for(String spawners : MobRegistry.getAllEntities().keySet()) {
                    p.sendMessage(Util.getPrefix() + spawners);
                }
            }
            if(strings.length == 1) {
                if(!(MobRegistry.getAllEntities().containsKey(strings[0]))) {
                    p.sendMessage(Util.getPrefix() + "This oneiron mob does not exist!");
                    return true;
                }
                p.sendMessage(Util.getPrefix() + "Spawned a new mob!");
                MobRegistry.spawnEntity(MobRegistry.getAllEntities().get(strings[0]), p.getLocation());
            }
        }
        return true;
    }
}
