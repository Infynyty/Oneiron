package de.kasyyy.oneiron.custommobs;

import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import de.kasyyy.oneiron.custommobs.mobs.ExchangeMerchant;
import de.kasyyy.oneiron.custommobs.mobs.ForestSpider;
import de.kasyyy.oneiron.custommobs.mobs.WeakSlime;
import de.kasyyy.oneiron.custommobs.mobs.WeakZombie;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.util.Util;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Map;
import java.util.Objects;

public class MobRegistry {

    private MobRegistry() {}


    //Saves all Entitytypes so that they can be called by a string
    private static HashBiMap<String, EntityTypes> allEntities = HashBiMap.create();


    public static org.bukkit.entity.Entity spawnEntity(EntityTypes entityTypes, Location loc) {
        Entity nmsEntity = entityTypes.spawnCreature(((CraftWorld) Objects.requireNonNull(loc.getWorld())).getHandle(),
                null,
                null,
                null,
                new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()),
                EnumMobSpawn.MOB_SUMMONED,
                true,
                false);

        OneironMobTemplate oneironMobTemplate = OneironMobTemplate.getGetOMbyName().get(entityTypes.a(((CraftWorld) loc.getWorld()).getHandle()).getCustomName().g().getString());
        OneironMob oneironMob = new OneironMob(oneironMobTemplate, (LivingEntity) nmsEntity.getBukkitEntity());
       nmsEntity.getBukkitEntity().setMetadata(Util.ID, new FixedMetadataValue(Oneiron.getInstance(), oneironMob.getId()));
       nmsEntity.getBukkitEntity().setCustomName(ChatColor.GRAY + "[Lvl. " + oneironMob.getLevel() + "] " + oneironMob.getName());

       return nmsEntity != null ? nmsEntity.getBukkitEntity() : null; // convert to a Bukkit entity
    }

    private static void injectNewEntity(String name, String extend_from) {
        Map<String, Type<?>> types = (Map<String, Type<?>>) DataConverterRegistry.a().getSchema(DataFixUtils.makeKey(SharedConstants.a().getWorldVersion())).findChoiceType(DataConverterTypes.ENTITY).types();
        types.put("minecraft:" + name, types.get("minecraft:" + extend_from));
        Type<?> type = types.get("minecraft:" + name);

    }

    public static void registerAllMobs() {

        EntityTypes WEAK_ZOMBIE;
        final String WEAK_ZOMBIE_NAME = "WeakZombie";

        String zombieExtends = "zombie";
        injectNewEntity(WEAK_ZOMBIE_NAME, zombieExtends);
        EntityTypes.a<EntityZombie> customZombie = EntityTypes.a.a(WeakZombie::new, EnumCreatureType.MONSTER);
        WEAK_ZOMBIE = IRegistry.a(IRegistry.ENTITY_TYPE, zombieExtends, customZombie.a(zombieExtends));
        allEntities.put(WEAK_ZOMBIE_NAME, WEAK_ZOMBIE);

        EntityTypes WEAK_SLIME;
        final String WEAK_SLIME_NAME = "WeakSlime";

        String slimeExtends = "slime";
        injectNewEntity(WEAK_SLIME_NAME, slimeExtends);
        EntityTypes.a<EntitySlime> weakSlime = EntityTypes.a.a(WeakSlime::new, EnumCreatureType.MONSTER);
        WEAK_SLIME = IRegistry.a(IRegistry.ENTITY_TYPE, slimeExtends, weakSlime.a(slimeExtends));
        allEntities.put(WEAK_SLIME_NAME, WEAK_SLIME);

        EntityTypes FOREST_SPIDER;
        final String FOREST_SPIDER_NAME ="ForestSpider";

        String spiderExtends = "spider";
        injectNewEntity(FOREST_SPIDER_NAME, spiderExtends);
        EntityTypes.a<EntitySpider> forestSpider = EntityTypes.a.a(ForestSpider::new, EnumCreatureType.MONSTER);
        FOREST_SPIDER = IRegistry.a(IRegistry.ENTITY_TYPE, spiderExtends, forestSpider.a(spiderExtends));
        allEntities.put(FOREST_SPIDER_NAME, FOREST_SPIDER);

        EntityTypes EXCHANGE_MERCHANT;
        final String EXCHANGE_MERCHANT_NAME = "ExchangeMerchant";
        String villagerExtends = "villager";

        injectNewEntity(EXCHANGE_MERCHANT_NAME, spiderExtends);
        EntityTypes.a<EntityVillagerTrader> exchangeMerchant = EntityTypes.a.a(ExchangeMerchant::new, EnumCreatureType.CREATURE);
        EXCHANGE_MERCHANT = IRegistry.a(IRegistry.ENTITY_TYPE, villagerExtends, exchangeMerchant.a(villagerExtends));
        allEntities.put(EXCHANGE_MERCHANT_NAME, EXCHANGE_MERCHANT);
    }

    public static HashBiMap<String, EntityTypes> getAllEntities() {
        return allEntities;
    }
}

