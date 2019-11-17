package de.kasyyy.oneiron.player;

import de.kasyyy.oneiron.main.Oneiron;

import java.util.UUID;

public class OneironPlayer {
    private final String name;
    private final UUID uuid;
    private int level, health, mana, xp;
    private Race.Races race;


    private Oneiron instance = Oneiron.getInstance();

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public Race.Races getClasses() {
        return race;
    }

    public int getXp() {
        return xp;
    }


    public OneironPlayer(String name, UUID uuid, int level, int health, int mana, int xp, Race.Races race) {
        this.name = name;
        this.uuid = uuid;
        this.level = level;
        this.health = health;
        this.mana = mana;
        this.xp = xp;
        this.race = race;
    }

    public OneironPlayer (UUID uuid) {
        if(instance.getConfig().contains(uuid.toString())) {
            this.uuid = uuid;
            this.race = Race.Races.valueOf(instance.getConfig().getString(uuid.toString() + ".Class"));
            this.health = instance.getConfig().getInt(uuid.toString() + ".Health");
            this.level = instance.getConfig().getInt(uuid.toString() + ".Level");
            this.mana = instance.getConfig().getInt(uuid.toString() + ".Mana");
            this.xp = instance.getConfig().getInt(uuid.toString() + ".XP");
            this.name = instance.getConfig().getString(uuid.toString() + ".Name");
        } else {
            throw new NullPointerException("No player with this UUID");
        }
    }

    public void saveToConfig(){
        instance.getConfig().set(uuid.toString() + ".Class", race.toString());
        instance.getConfig().set(uuid.toString() + ".Health", health);
        instance.getConfig().set(uuid.toString() + ".Level", level);
        instance.getConfig().set(uuid.toString() + ".Mana", mana);
        instance.getConfig().set(uuid.toString() + ".XP", xp);
        instance.getConfig().set(uuid.toString() + ".Name", name);
        instance.saveConfig();
    }

    //TODO: remove safeConfig


    public void setLevel(int level) {
        this.level = level;
        instance.saveConfig();
    }

    public void setHealth(int health) {
        this.health = health;
        instance.saveConfig();
    }

    public void setMana(int mana) {
        this.mana = mana;
        instance.saveConfig();
    }

    public void setXp(int xp) {
        this.xp = xp;
        instance.saveConfig();
    }

    public void setClasses(Race.Races race) {
        this.race = race;
        instance.saveConfig();
    }

}
