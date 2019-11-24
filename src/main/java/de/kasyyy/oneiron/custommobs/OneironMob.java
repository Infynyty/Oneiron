package de.kasyyy.oneiron.custommobs;

import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class OneironMob {
    private int health;
    private int damage;
    private int level;
    private int id;
    private static int counter = 0;
    private LivingEntity entity;
    private ArrayList<ItemStack> drops = new ArrayList<>();


    private static HashMap<Integer, OneironMob> oneironMobs = new HashMap<>();

    public OneironMob(int health, int damage, int level, LivingEntity entity) {
        this.health = health;
        this.damage = damage;
        this.level = level;
        this.entity = entity;
        id = counter;
        counter++;
        oneironMobs.put(id, this);
    }

    public OneironMob(int health, int damage, int level, LivingEntity entity, ArrayList<ItemStack> drops) {
        this.health = health;
        this.damage = damage;
        this.level = level;
        this.id = id;
        this.entity = entity;
        this.drops = drops;
        id = counter;
        counter++;
        oneironMobs.put(id, this);
    }

    public void damageEntity(int damageDealt, org.bukkit.entity.Entity damager) {
        if(health - damageDealt > 0) {
            health -= damageDealt;
            entity.damage(0);
            damager.sendMessage(Util.getDebug() + "Damage was: " + damageDealt + "; health was: " + health);
        } else {
            entity.setHealth(0);
            oneironMobs.remove(id);
        }

    }

    public int getId() {
        return id;
    }

    public static HashMap<Integer, OneironMob> getOneironMobs() {
        return oneironMobs;
    }


    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<ItemStack> getDrops() {
        return drops;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }
}
