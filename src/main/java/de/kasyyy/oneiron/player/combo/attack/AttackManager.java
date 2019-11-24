package de.kasyyy.oneiron.player.combo.attack;

import de.kasyyy.oneiron.player.Races;
import org.bukkit.Particle;

public class AttackManager {

    private AttackManager() {}

    //Explosions
    private static final Attack EXPLOSION_LV_1 = new Explosion(10, 20, 10, "Weak Explosion", Particle.EXPLOSION_NORMAL, Races.MAGE);
    private static final Attack EXPLOSION_LV_2 = new Explosion(15, 25, 12, "Normal Explosion", Particle.EXPLOSION_LARGE, Races.MAGE);
    private static final Attack EXPLOSION_LV_3 = new Explosion(20, 35, 15, "Huge Explosion", Particle.EXPLOSION_HUGE, Races.MAGE);

    //Slams
    private static final Attack SLAM_LV_1 = new Slam(10, 0, 10, "Weak Slam", Particle.DRAGON_BREATH, Races.ARCHER);

    //Lighting bolts
    private final static Attack LIGHTNING_LV_1 = new Lightning(50, 30, 20, "Weak Lighning", null, Races.MAGE);


    public static Attack getExplosionLv1() {
        return EXPLOSION_LV_1;
    }

    public static Attack getExplosionLv2() {
        return EXPLOSION_LV_2;
    }

    public static Attack getExplosionLv3() {
        return EXPLOSION_LV_3;
    }

    public static Attack getSlamLv1() {
        return SLAM_LV_1;
    }

    public static Attack getLightningLv1() {
        return LIGHTNING_LV_1;
    }
}
