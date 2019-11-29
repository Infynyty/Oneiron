package de.kasyyy.oneiron.player;

import de.kasyyy.oneiron.items.OneironCurrency;
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

import java.util.ArrayList;
import java.util.UUID;

public class OneironPlayer {
    private final String name;
    private final UUID uuid;
    private int level, maxHealth, maxMana, xp, health, mana;
    private boolean isInvincible = false;
    private Races race;
    private Attack attack1, attack2, attack3, attack4;
    private static ArrayList<UUID> playerRegenerating = new ArrayList<>();

    private Oneiron instance = Oneiron.getInstance();


    public OneironPlayer(String name, UUID uuid, int level, int health, int mana, int xp, Races race) {
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
            this.race = Races.valueOf(instance.getConfig().getString(uuid.toString() + ".Class"));
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
        Bukkit.getConsoleSender().sendMessage(Util.getDebug() + "Added xp");

        while(xp > instance.getConfig().getInt(path)) {
            if(level >= 20) break;
            xp = xp - instance.getConfig().getInt(path);
            level ++;
            path = "Level." + level;
            saveToConfig();
            levelUp();
            Bukkit.getPlayer(uuid).sendMessage(Util.getPrefix() + "You levelled up! Your new level is: " + level);
        }
    }

    private void levelUp() {
        Player p = Bukkit.getPlayer(uuid);
        p.setLevel(level);
        p.getLocation().getWorld().spawnParticle(Particle.TOTEM, p.getLocation().add(0, 3, 0), 20);
        p.getLocation().getWorld().playSound(p.getLocation(), Sound.BLOCK_BELL_USE, 10.0F, 1.0F);
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

    public void setRace(Races race) {
        switch (race) {
            case MAGE:
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
}
