package de.kasyyy.oneiron.player.combo.attack.mage;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.player.JoinListener;
import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.player.combo.attack.Attack;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class BasicFlame extends Attack {

    /**
     * The base attack for the mage class.
     * {@inheritDoc}
     */
    public BasicFlame(double damagePercent, int range, int manaCost, String name, Particle particle, Races races) {
        super(damagePercent, range, manaCost, name, particle, races);
    }

    @Override
    protected void attack(final Player p) {
        Set<Material> ignored = new HashSet<>();
        ignored.add(Material.TALL_GRASS);
        ignored.add(Material.GRASS);
        ignored.add(Material.AIR);
        ignored.add(Material.WATER);
        Location location = p.getTargetBlock(ignored, range).getLocation();

        //Checks the players line of sight in a radius of range to find out if an entity is targeted
        for (Block block : p.getLineOfSight(ignored, range)) {
            if (!(block.getType().equals(Material.AIR))) continue;
            if (!(block.getLocation().getWorld().getNearbyEntities(block.getLocation(), .5, .5, .5).isEmpty())) {
                Collection<Entity> target = block.getLocation().getWorld().getNearbyEntities(block.getLocation(), .5, .5, .5);
                target.removeIf(x -> x.equals(p));
                if (target.stream().anyMatch(x -> x instanceof LivingEntity)) {
                    LivingEntity entity = (LivingEntity) target.stream().filter(x -> x instanceof LivingEntity).findFirst().get();
                    location = entity.getLocation();
                    break;
                }
            }
        }
        //Spawns a lightning bolt
        p.getLocation().getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 1);
        p.getLocation().getWorld().playSound(location, Sound.BLOCK_CAMPFIRE_CRACKLE, 1.0F, 1.0F);
        List<Entity> entities = (List<Entity>) location.getWorld().getNearbyEntities(location, (float) 1, (float) 1, (float) 1, x -> x.hasMetadata(Util.ID));

        for (Entity entity : entities) {
            OneironMob oneironMob = OneironMob.getOneironMobs().get(entity.getMetadata(Util.ID).get(0).asInt());
            if (oneironMob != null) {
                super.attackOM(JoinListener.getAllOneironPlayers().get(p.getUniqueId()), oneironMob);
            }
        }

    }
}
