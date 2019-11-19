package de.kasyyy.oneiron.player;

import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.combo.attack.Attack;
import de.kasyyy.oneiron.player.combo.attack.AttackManager;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

public class OneironPlayer {
    private final String name;
    private final UUID uuid;
    private int level, maxHealth, maxMana, xp, health, mana;
    private Race.Races race;
    private Attack attack1, attack2, attack3, attack4;

    private Oneiron instance = Oneiron.getInstance();


    public OneironPlayer(String name, UUID uuid, int level, int health, int mana, int xp, Race.Races race) {
        this.name = name;
        this.uuid = uuid;
        this.level = level;
        this.maxHealth = health;
        this.maxMana = mana;
        this.xp = xp;
        this.race = race;

        this.health = this.maxHealth;
        this.mana = this.maxMana;
        setRace(race);
    }

    public OneironPlayer (UUID uuid) {
        if(instance.getConfig().contains(uuid.toString())) {
            this.uuid = uuid;
            this.race = Race.Races.valueOf(instance.getConfig().getString(uuid.toString() + ".Class"));
            this.maxHealth = instance.getConfig().getInt(uuid.toString() + ".Health");
            this.level = instance.getConfig().getInt(uuid.toString() + ".Level");
            this.maxMana = instance.getConfig().getInt(uuid.toString() + ".Mana");
            this.xp = instance.getConfig().getInt(uuid.toString() + ".XP");
            this.name = instance.getConfig().getString(uuid.toString() + ".Name");
            this.health = this.maxHealth;
            this.mana = this.maxMana;
            setRace(race);
        } else {
            throw new NullPointerException("No player with this UUID");
        }
    }



    public void saveToConfig(){
        instance.getConfig().set(uuid.toString() + ".Class", race.toString());
        instance.getConfig().set(uuid.toString() + ".Health", maxHealth);
        instance.getConfig().set(uuid.toString() + ".Level", level);
        instance.getConfig().set(uuid.toString() + ".Mana", maxMana);
        instance.getConfig().set(uuid.toString() + ".XP", xp);
        instance.getConfig().set(uuid.toString() + ".Name", name);
        instance.saveConfig();
    }

    //TODO: remove safeConfig

    /**
     * Use this method to damage an Oneiron player
     *
     * @param damageAmount The damage dealt
     */
    public void damage(int damageAmount) {
        if(health -damageAmount <= 0) {
            Objects.requireNonNull(Bukkit.getPlayer(uuid)).teleport(Objects.requireNonNull(Objects.requireNonNull(Bukkit.getPlayer(uuid)).getBedSpawnLocation()));
        } else {
            health -= damageAmount;
        }
    }

    /**
     * Use this method to subtract mana from the OneironPlayer
     *
     * @param manaAmount The amount subtracted
     * @return true: There was enough mana
     *         false: There wasn't enough mana left
     */
    public boolean removeMana(int manaAmount) {

        if(mana - manaAmount < 0) {
            Bukkit.broadcastMessage(Util.getDebug() + "Mana: " + mana + "; Needed: " + manaAmount);
            return false;
        } else {
            mana -= manaAmount;
            Bukkit.broadcastMessage(Util.getDebug() + "Mana: " + mana);
            return true;
        }
    }

    /**
     * Use this method to add mana to the OneironPlayer
     *
     * @param manaAmount The amount subtracted
     */
    public void addMana(int manaAmount) {
        if(mana + manaAmount >= maxMana) {
            mana = maxMana;
        } else {
            mana += manaAmount;
            Bukkit.broadcastMessage(Util.getDebug() + "Added mana: " + manaAmount);
        }
    }

    public void setLevel(int level) {
        this.level = level;
        instance.saveConfig();
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        instance.saveConfig();
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        instance.saveConfig();
    }

    public void setXp(int xp) {
        this.xp = xp;
        instance.saveConfig();
    }

    public void setRace(Race.Races race) {
        switch (race) {
            case MAGE:
                attack1 = AttackManager.getExplosionLv1();
                break;
            case ARCHER:
                attack1 = AttackManager.getSlamLv1();
                break;
            case NONE:
                break;
        }
        this.race = race;
        instance.saveConfig();
    }
    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHealth() {
        return maxHealth;
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

    public Attack getAttack1() {
        return attack1;
    }

    public Attack getAttack2() {
        return attack2;
    }

    public Attack getAttack3() {
        return attack3;
    }

    public Attack getAttack4() {
        return attack4;
    }

    public int getMaxMana() {
        return maxMana;
    }
}
