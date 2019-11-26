package de.kasyyy.oneiron.custommobs;

import de.kasyyy.oneiron.items.OneironItem;

import java.util.ArrayList;
import java.util.HashMap;

public class OneironMobTemplate {

    protected String name;
    protected int health, level, damage, xp;
    protected ArrayList<OneironItem> drops;
    protected static HashMap<String, OneironMobTemplate> getOMbyName = new HashMap<>();

    protected OneironMobTemplate(String name, int health, int level, int damage,ArrayList<OneironItem> drops, int xp) {
        this.name = name;
        this.health = health;
        this.level = level;
        this.damage = damage;
        this.drops = drops;
        this.xp = xp;
        getOMbyName.put(name, this);
    }

    public static HashMap<String, OneironMobTemplate> getGetOMbyName() {
        return getOMbyName;
    }

    public ArrayList<OneironItem> getDrops() {
        return drops;
    }
}
