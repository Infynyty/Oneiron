package de.kasyyy.oneiron.custommobs;

import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import de.kasyyy.oneiron.custommobs.mobs.WeakZombie;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.util.Util;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

public class MobRegistry {

    private MobRegistry() {}
    private static EntityTypes WEAK_ZOMBIE;

    public static final String WEAK_ZOMBIE_NAME = "WeakZombie";

    //Saves all Entitytypes so that they can be called by a string
    private static HashBiMap<String, EntityTypes> allEntities = HashBiMap.create();
    private static ArrayList<ItemStack> itemStacks = new ArrayList<>();

    static {
        itemStacks.add(Util.crItem(Material.EMERALD, 1, ChatColor.GREEN + "Sparkly gem", null));
    }


    public static org.bukkit.entity.Entity spawnEntity(EntityTypes entityTypes, Location loc, OneironMob oneironMob) {
        Entity nmsEntity = entityTypes.spawnCreature(((CraftWorld) Objects.requireNonNull(loc.getWorld())).getHandle(),
                null,
                null,
                null,
                new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()),
                EnumMobSpawn.MOB_SUMMONED,
                true,
                false);

        //TODO: Set oneiron mob here
       oneironMob.setEntity((LivingEntity) nmsEntity.getBukkitEntity());
       nmsEntity.getBukkitEntity().setMetadata(Util.ID, new FixedMetadataValue(Oneiron.getInstance(), oneironMob.getId()));

       return nmsEntity != null ? nmsEntity.getBukkitEntity() : null; // convert to a Bukkit entity
    }

    public static void injectNewEntity(String name, String extend_from) {
        Map<String, Type<?>> types = (Map<String, Type<?>>) DataConverterRegistry.a().getSchema(DataFixUtils.makeKey(SharedConstants.a().getWorldVersion())).findChoiceType(DataConverterTypes.ENTITY).types();
        types.put("minecraft:" + name, types.get("minecraft:" + extend_from));
        Type<?> type = types.get("minecraft:" + name);

    }

    public static void registerAllMobs() {

        String zombieExtends = "zombie";
        injectNewEntity(WEAK_ZOMBIE_NAME, zombieExtends);
        EntityTypes.a<EntityZombie> customZombie = EntityTypes.a.a(WeakZombie::new, EnumCreatureType.MONSTER);
        WEAK_ZOMBIE = IRegistry.a(IRegistry.ENTITY_TYPE, zombieExtends, customZombie.a(zombieExtends));
        allEntities.put(WEAK_ZOMBIE_NAME, WEAK_ZOMBIE);
    }

    public static HashBiMap<String, EntityTypes> getAllEntities() {
        return allEntities;
    }
}

