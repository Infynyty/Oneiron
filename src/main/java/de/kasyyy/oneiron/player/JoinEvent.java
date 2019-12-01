package de.kasyyy.oneiron.player;

import com.mojang.datafixers.TypeRewriteRule;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.combo.attack.Attack;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class JoinEvent implements Listener {
    private Oneiron instance = Oneiron.getInstance();
    private static HashMap<UUID, OneironPlayer> allOneironPlayers = new HashMap<>();

    //Tries to get the OneironPlayer from the config
    //If the player is not found there a new one will be created
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        OneironPlayer oneironPlayer = null;

        try(Connection connection = Oneiron.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM OneironPlayer WHERE uuid = ?"
        )) {

            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                oneironPlayer = new OneironPlayer(p.getUniqueId());
                p.sendMessage(Util.getDebug() + "Succesfully loaded player from config");

            } else {
                oneironPlayer = new OneironPlayer(p.getName(), p.getUniqueId(), 1, 100, 50, 1, Races.NONE);
                p.sendMessage(Util.getDebug() + "Successfully created new player");
                oneironPlayer.saveToConfig();

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(oneironPlayer == null) {
            Oneiron.getInstance().getLogger().log(Level.WARNING, "Unable to load player from config!");
            p.kickPlayer(Util.getPrefix() + ChatColor.RED + "An error occurred");
        }

        allOneironPlayers.put(oneironPlayer.getUuid(), oneironPlayer);
        if(oneironPlayer.getClasses().equals(Races.NONE)) {
            p.sendMessage(Util.getDebug() + "Please choose a class!");
            p.openInventory(Race.getChooseInv());
        }
    }

    //Removes the OnerionPlayer object
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if(allOneironPlayers.containsKey(e.getPlayer().getUniqueId())) {
            OneironPlayer oneironPlayer = allOneironPlayers.get(e.getPlayer().getUniqueId());
            oneironPlayer.saveToConfig();
            Bukkit.getConsoleSender().sendMessage(Util.getDebug() + "Saved Player to config");
            allOneironPlayers.remove(e.getPlayer().getUniqueId());
        }
        Attack.getPlayerRegenerating().remove(e.getPlayer().getUniqueId());
        OneironPlayer.getPlayerRegenerating().remove(e.getPlayer().getUniqueId());
    }

    public static HashMap<UUID, OneironPlayer> getAllOneironPlayers() {
        return allOneironPlayers;
    }

}
