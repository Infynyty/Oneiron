package de.kasyyy.oneiron.player;

import de.kasyyy.oneiron.items.OneironCurrency;
import de.kasyyy.oneiron.items.armor.OneironArmor;
import de.kasyyy.oneiron.items.weapons.OneironWeapon;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.combo.attack.Attack;
import de.kasyyy.oneiron.player.combo.attack.AttackManager;
import de.kasyyy.oneiron.util.Util;
import de.kasyyy.oneiron.util.runnables.HealthRegeneration;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class OneironPlayer {
    private final String name;
    private final UUID uuid;
    private int level, maxHealth, maxMana, xp, health, mana;
    private int speed, damage, defense;
    private boolean isInvincible = false;
    private Races race;
    private Attack basicAttack, attack1, attack2, attack3, attack4;
    private static ArrayList<UUID> playerRegenerating = new ArrayList<>();
    private OneironArmor[] armor = new OneironArmor[4];

    private Oneiron instance = Oneiron.getInstance();


    public OneironPlayer(String name, UUID uuid, int level, int health, int mana, int xp, Races race) {
        try(Connection connection = instance.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO OneironPlayer VALUES (" +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?)"
        )) {
            preparedStatement.setString(1, race.toString());
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.setInt(3, maxHealth);
            preparedStatement.setInt(4, maxMana);
            preparedStatement.setInt(5, damage);
            preparedStatement.setInt(6, speed);
            preparedStatement.setInt(7, defense);
            preparedStatement.setInt(8, level);
            preparedStatement.setInt(9, xp);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.name = name;
        this.uuid = uuid;
        this.level = level;
        this.maxHealth = health;
        this.maxMana = mana;
        this.xp = xp;
        this.race = race;

        this.health = this.maxHealth;
        this.mana = this.maxMana;
        this.damage = 0;
        this.speed = 1;
        this.defense = 0;
        setRace(race);
    }

    public OneironPlayer (UUID uuid) {
            this.uuid = uuid;
            this.name = null;

            try(Connection connection = instance.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM oneironPlayer WHERE uuid = ?")) {

                preparedStatement.setString(1, uuid.toString());
                ResultSet resultSet = preparedStatement.executeQuery();

                if(!resultSet.next()) throw new NullPointerException("No player with this UUID");

                do {
                    this.race = Races.valueOf(resultSet.getString("race"));
                    this.maxHealth = resultSet.getInt("maxHealth");
                    this.level = resultSet.getInt("level");
                    this.xp = resultSet.getInt("xp");
                    this.maxMana = resultSet.getInt("maxMana");
                    this.damage = resultSet.getInt("damage");
                    this.speed = resultSet.getInt("speed");
                    this.defense = resultSet.getInt("defense");
                } while(resultSet.next());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            this.health = this.maxHealth;
            this.mana = this.maxMana;
            setRace(race);
    }



    public void saveToConfig(){
        try(Connection connection = instance.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE OneironPlayer SET " +
                        "race = ?, " +
                        "level = ?, " +
                        "maxHealth = ?, " +
                        "maxMana = ?, " +
                        "damage = ?, " +
                        "speed = ?, " +
                        "defense = ?, " +
                        "level = ?, " +
                        "xp = ? " +
                        "WHERE uuid = ?"
        )) {
            preparedStatement.setString(1, race.toString());
            preparedStatement.setInt(2, level);
            preparedStatement.setInt(3, maxHealth);
            preparedStatement.setInt(4, maxMana);
            preparedStatement.setInt(5, damage);
            preparedStatement.setInt(6, speed);
            preparedStatement.setInt(7, defense);
            preparedStatement.setInt(8, level);
            preparedStatement.setInt(9, xp);
            preparedStatement.setString(10, uuid.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Use this method to damage an Oneiron player
     *
     * @param damageAmount The damage dealt
     */
    public void damagePlayer(int damageAmount) {
        if(isInvincible) return;
        int result = health - damageAmount;
        if(result <= 0) {
            health = maxHealth;
            mana = maxMana;
            Player p = Bukkit.getPlayer(uuid);
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*8, 3, true, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*3, 5, true, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20*10, 1, true, false));
            p.setVelocity(new Vector(0, 0, 0));
            p.getLocation().getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3.0F, 1.0F);
            p.teleport(p.getWorld().getSpawnLocation());
            isInvincible = true;
            new BukkitRunnable() {
                @Override
                public void run() {
                    isInvincible = false;
                    if(Bukkit.getPlayer(uuid) == null) cancel();
                    Bukkit.getPlayer(uuid).sendMessage(Util.getPrefix() + "You are no longer protected!");
                }
            }.runTaskLater(Oneiron.getInstance(), 20*8);

            int i = 0;
            for(ItemStack itemStack : p.getInventory().getContents()) {
                if(itemStack == null) continue;
                if(i > level * 2) break;
                if(itemStack.isSimilar(OneironCurrency.SCREW.getItemStack())) {
                    while(itemStack.getAmount() > 0) {
                        if(i > level * 2) break;
                        i++;
                        itemStack.setAmount(itemStack.getAmount() - 1);
                    }
                }
                if(itemStack.isSimilar(OneironCurrency.SCRAP_METAL.getItemStack())) {
                    while(itemStack.getAmount() > 0) {
                        if(i > level * 2) break;
                        i += 64;
                        itemStack.setAmount(itemStack.getAmount() - 1);
                    }
                }
            }
        } else {
            health -= damageAmount;
            float percent = (float) health / (float) maxHealth;

            //Because of rounding the health might be zero before the oneiron health is
            if(Math.round(20*percent) <= 0) {
                Bukkit.getPlayer(uuid).setHealth(1);
            } else {
                Bukkit.getPlayer(uuid).setHealth(Math.round(20 * percent));
            }
            if(!playerRegenerating.contains(uuid)) {
                new HealthRegeneration(this, 10, playerRegenerating).runTaskTimer(Oneiron.getInstance(), 20 * 3, 20);
            }

        }
    }

    /**
     * Use this method to heal an Oneiron player
     * @param healAmount The amount of health to be regenerated
     */
    public void heal(int healAmount) {
        Player player = Bukkit.getPlayer(uuid);
        float percent = (float) health / (float) maxHealth;
        if(health + healAmount >= maxHealth) {
            health = maxHealth;
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
        } else {
            if(health + healAmount > 0) {
                player.setHealth(Math.round(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue() * percent));
                health += healAmount;
            }
        }
    }

    /**
     * Use this method to subtract mana from the Oneiron Player
     *
     * @param manaAmount The amount subtracted
     * @return true: There was enough mana
     *         false: There wasn't enough mana left
     */
    public boolean removeMana(int manaAmount) {

        if(mana - manaAmount < 0) {
            return false;
        } else {
            mana -= manaAmount;
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
        }
    }

    /**
     * Gives xp to a player and levels him up if the level border is crossed
     * @param xpAmount The amount of xp
     */
    public void addXP(int xpAmount) {
        String path = "Level." + level;
        xp += xpAmount;
        saveToConfig();

        while(xp > instance.getConfig().getInt(path)) {
            if(level >= 20) break;
            xp = xp - instance.getConfig().getInt(path);
            level ++;
            path = "Level." + level;
            saveToConfig();
            levelUp();
            Bukkit.getPlayer(uuid).sendMessage(Util.getPrefix() + "You leveled up! Your new level is: " + level);
        }
    }

    private void levelUp() {
        Player p = Bukkit.getPlayer(uuid);
        p.setLevel(level);
        p.getLocation().getWorld().spawnParticle(Particle.TOTEM, p.getLocation().add(0, 3, 0), 20);
        p.getLocation().getWorld().playSound(p.getLocation(), Sound.BLOCK_BELL_USE, 10.0F, 1.0F);
    }

    /**
     * Get the players damage which includes the damage of the weapon they are holding.
     * @return
     */
    public int getDamage() {
        Player player = Bukkit.getPlayer(uuid);
        OneironWeapon oneironWeapon = OneironWeapon.getOWFromIS().get(player.getItemInHand());
        if(oneironWeapon != null) {
            return Math.round(oneironWeapon.getDamage() + damage);
        } else {
            return 0;
        }
    }

    /**
     * Use this method to get the raw damage of the player, which doesn't include the weapon damage.
     * @return
     */
    public int getRawDamage() {
        return damage;
    }

    public void removeArmorEffects() {
        for(ItemStack itemStack : Bukkit.getPlayer(uuid).getInventory().getArmorContents()) {
            OneironArmor armor = OneironArmor.getoAFromIS().get(itemStack);

            if(armor != null) {

                setMaxHealth(getMaxHealth() - armor.getAddedHealth());
                setMaxMana(getMaxMana() - armor.getAddedMana());
                setDefense(getDefense() - armor.getAddedDefense());
                setSpeed(getSpeed() - armor.getAddedSpeed());
                setDamage(getRawDamage() - armor.getAddedDamage());

            }
        }
    }


    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Set the maximum amount of health a player can have. If the new amount is smaller than his current health,
     * the current health will be set to the maximum health.
     * @param maxHealth
     */
    public void setMaxHealth(int maxHealth) {
        if(maxHealth < health) {
            health = maxHealth;
        }
        this.maxHealth = maxHealth;
    }

    /**
     * Set the maximum amount of mana a player can have. If the new amount is smaller than his current mana,
     * the current mana will be set to the maximum mana.
     * @param maxMana
     */
    public void setMaxMana(int maxMana) {
        if(maxMana < mana) {
            mana = maxMana;
        }
        this.maxMana = maxMana;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setRace(Races race) {
        switch (race) {
            case MAGE:
                basicAttack = AttackManager.getFLAME();
                attack1 = AttackManager.getRageLv1();
                attack2 = AttackManager.getLightningLv1();
                attack3 = AttackManager.getHealLv1();
                attack4 = AttackManager.getSlamLv1();
                break;
            case ARCHER:
                attack1 = AttackManager.getSlamLv1();
                break;
            case WARRIOR:
                //TODO: add functionality
                break;
            case NONE:
                break;
        }
        this.race = race;
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

    public Races getClasses() {
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

    public int getHealth() {
        return health;
    }

    public static ArrayList<UUID> getPlayerRegenerating() {
        return playerRegenerating;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public Attack getBasicAttack() {
        return basicAttack;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public OneironArmor[] getArmor() {
        return armor;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
