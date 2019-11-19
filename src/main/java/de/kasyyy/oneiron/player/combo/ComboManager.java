package de.kasyyy.oneiron.player.combo;

import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.JoinEvent;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.player.combo.attack.AttackManager;
import de.kasyyy.oneiron.util.Util;
import de.kasyyy.oneiron.util.runnables.DelayedTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ComboManager implements Listener {

    private Oneiron instance = Oneiron.getInstance();

    public HashMap<UUID, Integer> getComboPoints() {
        return comboPoints;
    }

    private HashMap<UUID, Integer> comboPoints = new HashMap<>();
    private ArrayList<UUID> coolDown = new ArrayList<>();
    private BukkitTask cancelTask;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        comboPoints.put(e.getPlayer().getUniqueId(), 1);
        e.getPlayer().getInventory().addItem(TESTINGComboItems.getSword());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        comboPoints.remove(e.getPlayer().getUniqueId());
    }

    //Depending on the points it is possible to see which part of the combo a player is in
    //1 point: Right or Left (use D for debugging) (not important because each class only has either R or L to start a combo)
    //2 points: A player has clicked once already, clicks a second time; depending on L or R he gets 1 or 2 points
    //3 points: the combo DRD
    //4 Points: the combo DLD

    @SuppressWarnings("Deprecated")
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if(!(TESTINGComboItems.getClassItems().contains(e.getPlayer().getItemInHand()))) return;

        comboPoints.putIfAbsent(e.getPlayer().getUniqueId(), 1); //Prevents a player from not being in the Hashmap
        Player p = e.getPlayer();
        if(!(JoinEvent.getAllPlayers().containsKey(p.getUniqueId()))) {
            p.kickPlayer(Util.getErrReload());
            return;
        }
        OneironPlayer oneironPlayer = JoinEvent.getAllPlayers().get(p.getUniqueId());

        if(coolDown.contains(p.getUniqueId())) return;
        coolDown.add(p.getUniqueId());
        switch (comboPoints.get(p.getUniqueId())) {
            case 1:
                comboPoints.put(p.getUniqueId(), 2);
                break;
            case 2:
                if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    comboPoints.put(p.getUniqueId(), comboPoints.get(p.getUniqueId()) + 1);
                    break;
                }
                if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    comboPoints.put(p.getUniqueId(), comboPoints.get(p.getUniqueId()) + 2);
                    break;
                }
            case 3:
                if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    comboPoints.put(p.getUniqueId(), 1);
                    p.sendMessage(Util.getDebug() + "Used attack");
                    oneironPlayer.getAttack1().attackUsed(oneironPlayer);
                    break;
                }
                if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    comboPoints.put(p.getUniqueId(), 1);
                    AttackManager.getSlamLv1().attackUsed(oneironPlayer);
                    break;
                }
            case 4:
                if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    comboPoints.put(p.getUniqueId(), 1);
                    break;
                }
                if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    comboPoints.put(p.getUniqueId(), 1);
                    break;
                }
            default:
                comboPoints.put(p.getUniqueId(), 1);
        }

        //Prevents a player from clicking too fast
        instance.getServer().getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
            @Override
            public void run() {
                coolDown.remove(p.getUniqueId());
            }
        }, 0);

        //If the task is running, it is cancelled and restarted
        if(cancelTask != null && instance.getServer().getScheduler().isQueued(cancelTask.getTaskId()))
        {
            instance.getServer().getScheduler().cancelTask(cancelTask.getTaskId());
            cancelTask = null;
        }
        //If the task is not cancelled within 3 seconds the player's combo score is set to 0 again
        //so that the player does not stay in the combo for ever

        cancelTask = new DelayedTask(p, comboPoints).runTaskTimer(instance, 0, 30);
    }

}
