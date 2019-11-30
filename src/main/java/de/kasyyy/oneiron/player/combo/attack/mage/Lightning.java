package de.kasyyy.oneiron.player.combo.attack.mage;

import de.kasyyy.oneiron.custommobs.OneironMob;
import de.kasyyy.oneiron.items.weapons.OneironWeapon;
import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.player.combo.attack.Attack;
import de.kasyyy.oneiron.util.Util;
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

public class Lightning extends Attack {


    public Lightning(double damagePercent, int range, int manaCost, String name, Particle particle, Races races) {
        super(damagePercent, range, manaCost, name, particle, races);
    }

    @Override
    protected void attack(Player p) {
        //All materials which are ignored during the check for the attack location
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
        p.getLocation().getWorld().strikeLightningEffect(location);
        p.getLocation().getWorld().playSound(location, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.1F, 0.1F);
        List<Entity> entities = (List<Entity>) location.getWorld().getNearbyEntities(location, (float) range / 5, (float) range / 5, (float) range / 5, x -> x.hasMetadata(Util.ID));

        for (Entity entity : entities) {
            OneironMob oneironMob = OneironMob.getOneironMobs().get(entity.getMetadata(Util.ID).get(0).asInt());
            if (oneironMob != null) {
                oneironMob.damageEntity((int) Math.round(OneironWeapon.getOWFromIS().get(p.getItemInHand()).getDamage() * damagePercent), p);
            }
        }
    }
}
