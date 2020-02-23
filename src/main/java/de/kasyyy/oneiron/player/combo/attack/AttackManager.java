package de.kasyyy.oneiron.player.combo.attack;

import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.player.combo.attack.mage.BasicFlame;
import de.kasyyy.oneiron.player.combo.attack.mage.Heal;
import de.kasyyy.oneiron.player.combo.attack.mage.Lightning;
import de.kasyyy.oneiron.player.combo.attack.mage.Slam;
import org.bukkit.Particle;

public final class AttackManager {

    private AttackManager() {

    }

    //Flame
    private static final Attack FLAME = new BasicFlame(1.0D, 0, 0, "Basic Flame", null, Races.MAGE);

    //Explosions
    private static final Attack EXPLOSION_LV_1 = new Explosion(2.0D, 20, 10, "Weak Explosion", Particle.EXPLOSION_NORMAL, Races.MAGE);
    private static final Attack EXPLOSION_LV_2 = new Explosion(2.0D, 25, 12, "Normal Explosion", Particle.EXPLOSION_LARGE, Races.MAGE);
    private static final Attack EXPLOSION_LV_3 = new Explosion(2.0D, 35, 15, "Huge Explosion", Particle.EXPLOSION_HUGE, Races.MAGE);

    //Slams
    private final static Attack SLAM_LV_1 = new Slam(0.5D, 0, 10, "Weak Slam", Particle.DRAGON_BREATH, Races.ARCHER);

    //Lighting bolts
    private final static Attack LIGHTNING_LV_1 = new Lightning(4.0D, 30, 40, "Weak Lightning", null, Races.MAGE);

    //Heal
    private static final Attack HEAL_LV_1 = new Heal(0, 0, 10, "Weak healing", Particle.FIREWORKS_SPARK, Races.MAGE);

    //Rage
    private static final Attack RAGE_LV_1 = new Rage(2.0D, 4, 10, "Rage", null, Races.WARRIOR);

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

    public static Attack getHealLv1() {
        return HEAL_LV_1;
    }

    public static Attack getRageLv1() {
        return RAGE_LV_1;
    }

    public static Attack getFLAME() {
        return FLAME;
    }
}
