package de.kasyyy.oneiron.custommobs;

import de.kasyyy.oneiron.items.OneironItem;
import de.kasyyy.oneiron.items.armor.OneironArmor;
import de.kasyyy.oneiron.player.JoinListener;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class OneironMob extends OneironMobTemplate{
    private int health, maxHealth, damage, level, id, xp;
    private static int counter = 0;
    private LivingEntity entity;
    private ArrayList<OneironItem> drops;
    private static HashMap<String, OneironMob> getOMbyName = new HashMap<>();
    private String name;

    private static HashMap<Integer, OneironMob> oneironMobs = new HashMap<>();


    public OneironMob(OneironMobTemplate oneironMobTemplate, LivingEntity entity) {
        super(oneironMobTemplate.name, oneironMobTemplate.health, oneironMobTemplate.level, oneironMobTemplate.damage, oneironMobTemplate.drops, oneironMobTemplate.xp);
        this.xp = oneironMobTemplate.xp;
        this.drops = oneironMobTemplate.drops;
        this.damage = oneironMobTemplate.damage;
        this.level = oneironMobTemplate.level;
        this.name = oneironMobTemplate.name;
        this.health = oneironMobTemplate.health;
        this.entity = entity;
        this.maxHealth = health;
        id = counter;
        counter++;
        oneironMobs.put(id, this);
    }


    public void damageEntity(int damageDealt, org.bukkit.entity.Entity damager) {
        if(this.getEntity().hasMetadata(Util.INVULNERABLE)) return;
        if(this.health - damageDealt > 0) {
            this.health -= damageDealt;
            this.entity.setCustomName(this.name + ChatColor.AQUA +  " [" + this.health + "/" + maxHealth + "]");
            this.entity.damage(0);
        } else {
            this.entity.setHealth(0);
            oneironMobs.remove(id);
            if(!(damager instanceof Player)) return;
            if(drops.size() > 0) {
                for (OneironItem oneironItem : drops) {
                    int i = ThreadLocalRandom.current().nextInt(1000);
                    if (i < oneironItem.getDropChance()) {
                        this.entity.getLocation().getWorld().dropItem(this.getEntity().getLocation(), oneironItem.getItemStack());
                        this.entity.getLocation().getWorld().dropItem(this.getEntity().getLocation(), OneironArmor.getAllArmorPieces().get(0).getItemStack());
                    }
                }
            }
            OneironPlayer oneironPlayer = JoinListener.getAllOneironPlayers().get(damager.getUniqueId());
            oneironPlayer.addXP(this.xp);
        }

    }

    public int getId() {
        return id;
    }

    public static HashMap<Integer, OneironMob> getOneironMobs() {
        return oneironMobs;
    }


    public int getHealth() {
        return this.health;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getLevel() {
        return this.level;
    }

    public ArrayList<OneironItem> getDrops() {
        return this.drops;
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }

    public String getName() {
        return name;
    }
}
