package de.kasyyy.oneiron.player;

import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class JoinEvent implements Listener {
    private Oneiron instance = Oneiron.getInstance();
    private static HashMap<UUID, OneironPlayer> allPlayers = new HashMap<>();

    //Tries to get the OneironPlayer from the config
    //If the player is not found there a new one will be created
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        OneironPlayer oneironPlayer;
        if(instance.getConfig().contains(p.getUniqueId().toString())) {
            oneironPlayer = new OneironPlayer(p.getUniqueId());
            p.sendMessage(Util.getDebug() + "Succesfully loaded player from config");
        } else {
            oneironPlayer = new OneironPlayer(p.getName(), p.getUniqueId(), 1, 10, 10, 1, Race.Races.NONE);
            p.sendMessage(Util.getDebug() + "Successfully created new player");
            oneironPlayer.saveToConfig();
        }
        allPlayers.put(oneironPlayer.getUuid(), oneironPlayer);
        if(oneironPlayer.getClasses().equals(Race.Races.NONE)) {
            p.sendMessage(Util.getDebug() + "Please choose a class!");
            p.openInventory(Race.getChooseInv());
        }
    }

    //Removes the OnerionPlayer object
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        OneironPlayer oneironPlayer = allPlayers.get(e.getPlayer().getUniqueId());
        oneironPlayer.saveToConfig();
        allPlayers.remove(e.getPlayer().getUniqueId());
    }

    public static HashMap<UUID, OneironPlayer> getAllPlayers() {
        return allPlayers;
    }

}
