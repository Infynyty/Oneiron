package de.kasyyy.oneiron.custommobs.spawner;

import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class CMDspawnerdebug implements CommandExecutor {
    private static boolean debug = false;
    private static final Material DEBUG_MATERIAL = Material.GOLD_BLOCK;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        setDebugBlocks();
        return true;
    }

    /**
     * This method activates/deactivates, depending on whether it is active or not, the spawner debug mode.
     * All spawners are indicated by {@link CMDspawnerdebug#DEBUG_MATERIAL}.
     */
    public static void setDebugBlocks() {
        try(Connection connection = Oneiron.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM OneironSpawner"
        )) {

            if(!debug) {
                ResultSet resultSet = preparedStatement.executeQuery();
                debug = true;
                while (resultSet.next()) {
                    String world = resultSet.getString("world");
                    int x = resultSet.getInt("x");
                    int y = resultSet.getInt("y");
                    int z = resultSet.getInt("z");
                    Location location = new Location(Bukkit.getWorld(world), x, y, z);
                    location.getBlock().setType(DEBUG_MATERIAL);
                }
                Bukkit.getConsoleSender().sendMessage(Util.getPrefix() + "Enabled debug mode");
            } else {
                ResultSet resultSet = preparedStatement.executeQuery();
                debug = false;
                while (resultSet.next()) {
                    String world = resultSet.getString("world");
                    int x = resultSet.getInt("x");
                    int y = resultSet.getInt("y");
                    int z = resultSet.getInt("z");
                    Location location = new Location(Bukkit.getWorld(world), x, y, z);
                    location.getBlock().setType(Material.AIR);

                }
                Bukkit.getConsoleSender().sendMessage(Util.getPrefix() + "Disabled debug mode");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        CMDspawnerdebug.debug = debug;
    }

    public static Material getDebugMaterial() {
        return DEBUG_MATERIAL;
    }
}
