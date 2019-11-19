package de.kasyyy.oneiron.player.combo.attack;

import de.kasyyy.oneiron.player.Race;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Explosion extends Attack {


    public Explosion(int damage, int range, int manaCost, String name, Particle particle, Race.Races races) {
        super(damage, range, manaCost, name, particle, races);
    }

    @Override
    public void attack(Player p) {

        //All materials which are ignored during the check for the attack location
        Set<Material> ignored = new HashSet<>();
        ignored.add(Material.TALL_GRASS);
        ignored.add(Material.GRASS);
        ignored.add(Material.AIR);
        ignored.add(Material.WATER);
        Location location = p.getTargetBlock(ignored, range).getLocation();

        //Checks the players line of sight in a radius of range to find out if an entity is targeted
        for(Block block : p.getLineOfSight(ignored, range)) {
            if(!(block.getType().equals(Material.AIR))) continue;
            if(!(block.getLocation().getWorld().getNearbyEntities(block.getLocation(), .5, .5, .5).isEmpty())) {
                Collection<Entity> target = block.getLocation().getWorld().getNearbyEntities(block.getLocation(), .5, .5, .5);
                target.removeIf(x -> x.equals(p));
                if(target.stream().anyMatch(x -> x instanceof LivingEntity)) {
                    LivingEntity entity = (LivingEntity) target.stream().filter(x -> x instanceof LivingEntity).findFirst().get();
                    location = entity.getLocation();
                    break;
                }
            }
        }

        location.getWorld().spawnParticle(particle, location.add(0, 1,0 ), 1);
        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.5F, 1.5F);
        //All enemies near the attack location are attacked
        List<Entity> entities = (List<Entity>) location.getWorld().getNearbyEntities(location, (float) range/10, (float) range/10, (float) range/10);

        for(Entity entity: entities) {
            if(!(entity instanceof LivingEntity)) continue;
            if(entity.equals(p)) continue;
            if(((LivingEntity) entity).getHealth() - damage < 0) {
                ((LivingEntity) entity).setHealth(0);
            } else {
                ((LivingEntity) entity).setHealth(((LivingEntity) entity).getHealth() - damage);
            }
        }

    }
}
