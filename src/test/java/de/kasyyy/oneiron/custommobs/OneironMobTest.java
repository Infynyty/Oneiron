package de.kasyyy.oneiron.custommobs;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.player.Races;
import net.minecraft.server.v1_14_R1.EntityZombie;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

import static org.junit.Assert.*;

public class OneironMobTest {

    @Mock Entity entity;
    private OneironMobTemplate oneironMobTemplate1 = new OneironMobTemplate(null, 1, 1, 1, null, 1);
    private OneironMobTemplate oneironMobTemplate2 = new OneironMobTemplate(null, 1, 1, 1, null, 1);
    private OneironMob oneironMob1 = new OneironMob(oneironMobTemplate1, (LivingEntity) entity);
    private OneironMob oneironMob2 = new OneironMob(oneironMobTemplate2, (LivingEntity) entity);

    @Test
    public void mobsShouldNotHaveSameID() {
        assertNotEquals(oneironMob1.getId(), oneironMob2.getId());
    }

}
