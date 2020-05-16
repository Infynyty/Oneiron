package de.kasyyy.oneiron.player.events.changeArmor;

import de.kasyyy.oneiron.items.armor.OneironArmor;
import de.kasyyy.oneiron.player.JoinListener;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChangeArmorListener implements Listener {

    //TODO: Check if the clicked item is equiped or not: if it is add the effect, else remove it

    @EventHandler
    public void onChangeArmor(ChangeOneironArmorEvent e) {
        OneironPlayer oneironPlayer = JoinListener.getAllOneironPlayers().get(e.getPlayer().getUniqueId());
        OneironArmor oldArmor = OneironArmor.getoAFromIS().get(e.getOldArmorPiece());
        OneironArmor newArmor = OneironArmor.getoAFromIS().get(e.getNewArmorPiece());

        if(oldArmor != null) {
            e.getPlayer().sendMessage(Util.getDebug() + "Damage was: " + oneironPlayer.getRawDamage());
            e.getPlayer().sendMessage(Util.getDebug() + "Damage removed should be: " + oldArmor.getAddedDamage());

            oneironPlayer.setMaxHealth(oneironPlayer.getMaxHealth() - oldArmor.getAddedHealth());
            oneironPlayer.setMaxMana(oneironPlayer.getMaxMana() - oldArmor.getAddedMana());
            oneironPlayer.setDefense(oneironPlayer.getDefense() - oldArmor.getAddedDefense());
            oneironPlayer.setSpeed(oneironPlayer.getSpeed() - oldArmor.getAddedSpeed());

            e.getPlayer().sendMessage(Util.getDebug() + "Damage of the player " + oneironPlayer.getRawDamage());
            e.getPlayer().sendMessage(Util.getDebug() + "Damage of the armor " + oldArmor.getAddedDamage());
            e.getPlayer().sendMessage(Util.getDebug() + "Removing damage from player: " + (oneironPlayer.getRawDamage() - oldArmor.getAddedDamage()));


            oneironPlayer.setDamage(oneironPlayer.getRawDamage() - oldArmor.getAddedDamage());

            e.getPlayer().sendMessage(Util.getDebug() + "Damage after removing: " + oneironPlayer.getRawDamage());
        }
        if(newArmor != null) {
            oneironPlayer.setMaxHealth(oneironPlayer.getMaxHealth() + newArmor.getAddedHealth());
            oneironPlayer.setMaxMana(oneironPlayer.getMaxMana() + newArmor.getAddedMana());
            oneironPlayer.setDefense(oneironPlayer.getDefense() + newArmor.getAddedDefense());
            oneironPlayer.setSpeed(oneironPlayer.getSpeed() + newArmor.getAddedSpeed());
            oneironPlayer.setDamage(oneironPlayer.getRawDamage() + newArmor.getAddedDamage());

            e.getPlayer().sendMessage(Util.getDebug() + "Damage is: " + oneironPlayer.getRawDamage());
            e.getPlayer().sendMessage(Util.getDebug() + "Damage added is: " + newArmor.getAddedDamage());
        }


    }
}
