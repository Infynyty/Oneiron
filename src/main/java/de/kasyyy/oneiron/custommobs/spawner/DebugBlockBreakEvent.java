package de.kasyyy.oneiron.custommobs.spawner;

import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DebugBlockBreakEvent implements Listener {

    @EventHandler
    public void onDebugBreak(BlockBreakEvent e) {
        if(!CMDspawnerdebug.isDebug()) return;
        if(!(e.getBlock().getType().equals(CMDspawnerdebug.getDebugMaterial()))) return;
        try(Connection connection = Oneiron.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM OneironSpawner WHERE world = ? AND x = ? AND y = ? AND Z = ?"
        )) {
            preparedStatement.setString(1, e.getBlock().getLocation().getWorld().getName());
            preparedStatement.setInt(2, e.getBlock().getX());
            preparedStatement.setInt(3, e.getBlock().getY());
            preparedStatement.setInt(4, e.getBlock().getZ());
            int i = preparedStatement.executeUpdate();
            if(i != 0) {
                e.getPlayer().sendMessage(Util.getPrefix() + "Removed Spawner");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
