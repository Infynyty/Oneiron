package de.kasyyy.oneiron.player.events.changeArmor;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import de.kasyyy.oneiron.items.armor.OneironArmor;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.JoinListener;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.util.Util;
import de.kasyyy.oneiron.util.runnables.HealthRegeneration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChangeArmorListener implements Listener {

    //TODO: Check if the clicked item is equiped or not: if it is add the effect, else remove it

    @EventHandler
    public void onChangeArmor(PlayerArmorChangeEvent e) {
        OneironPlayer oneironPlayer = JoinListener.getAllOneironPlayers().get(e.getPlayer().getUniqueId());
        OneironArmor oldArmor = OneironArmor.getoAFromIS().get(e.getOldItem());
        OneironArmor newArmor = OneironArmor.getoAFromIS().get(e.getNewItem());

        if(oldArmor != null) {

            oneironPlayer.setMaxHealth(oneironPlayer.getMaxHealth() - oldArmor.getAddedHealth());
            oneironPlayer.setMaxMana(oneironPlayer.getMaxMana() - oldArmor.getAddedMana());
            oneironPlayer.setDefense(oneironPlayer.getDefense() - oldArmor.getAddedDefense());
            oneironPlayer.setSpeed(oneironPlayer.getSpeed() - oldArmor.getAddedSpeed());
            oneironPlayer.setDamage(oneironPlayer.getRawDamage() - oldArmor.getAddedDamage());
            e.getPlayer().sendMessage(Util.getDebug() + "Removed: Health: " + oneironPlayer.getMaxHealth());

        }
        if(newArmor != null) {
            oneironPlayer.setMaxHealth(oneironPlayer.getMaxHealth() + newArmor.getAddedHealth());
            oneironPlayer.setMaxMana(oneironPlayer.getMaxMana() + newArmor.getAddedMana());
            oneironPlayer.setDefense(oneironPlayer.getDefense() + newArmor.getAddedDefense());
            oneironPlayer.setSpeed(oneironPlayer.getSpeed() + newArmor.getAddedSpeed());
            oneironPlayer.setDamage(oneironPlayer.getRawDamage() + newArmor.getAddedDamage());
            e.getPlayer().sendMessage(Util.getDebug() + "Added: Health: " + oneironPlayer.getMaxHealth());
            if(!OneironPlayer.getPlayerRegenerating().contains(oneironPlayer.getUuid())) {
                new HealthRegeneration(oneironPlayer, 10, OneironPlayer.getPlayerRegenerating()).runTaskTimer(Oneiron.getInstance(), 20 * 3, 20);
            }
        }
    }
}
