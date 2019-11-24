package de.kasyyy.oneiron.player;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.player.Races;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

import java.util.UUID;

public class OneironPlayerTest {
    private OneironPlayer oneironPlayer = new OneironPlayer("Test", UUID.randomUUID(), 1, 1, 1, 1, Races.MAGE);

    @Test
    public void damageShouldNotBeLowerThanZero() {
        oneironPlayer.damage(10);
        assertTrue(oneironPlayer.getHealth() > 0);
        assertEquals(oneironPlayer.getHealth(), oneironPlayer.getMaxHealth());
    }

    @Test
    public void healthShouldNotBeBiggerThanMax() {
        oneironPlayer.heal(oneironPlayer.getMaxHealth());

        assertEquals(oneironPlayer.getHealth(), oneironPlayer.getMaxHealth());
    }

    @Test
    public void manaShouldNotBeLowerThanZero() {
        oneironPlayer.addMana(oneironPlayer.getMaxMana());

        assertEquals(oneironPlayer.getMana(), oneironPlayer.getMaxMana());
    }
}
