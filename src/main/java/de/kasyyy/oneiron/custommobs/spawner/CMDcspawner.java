package de.kasyyy.oneiron.custommobs.spawner;

import de.kasyyy.oneiron.custommobs.MobRegistry;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDcspawner implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(command.getName().equalsIgnoreCase("cspawner")) {
            if(!(commandSender instanceof Player)) return true;
            Player p = (Player) commandSender;
            if(strings.length == 0) {
                p.sendMessage(Util.getPrefix() + "Possible spawners:\n");
                for(String spawners : MobRegistry.getAllEntities().keySet()) {
                    p.sendMessage(Util.getPrefix() + spawners);
                }
            }
            if(strings.length == 1) {
                if(!(MobRegistry.getAllEntities().containsKey(strings[0]))) {
                    p.sendMessage(Util.getPrefix() + "This oneiron mob does not exist!");
                    return true;
                }
                p.sendMessage(Util.getPrefix() + "A new spawner has been created!");
                Spawner spawner = new Spawner(p.getLocation(), MobRegistry.getAllEntities().get(strings[0]));
                spawner.spawnMobs();
            }
        }
        return true;
    }
}
