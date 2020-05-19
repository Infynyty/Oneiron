package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.player.Races;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class OneironItem {
    private int value, dropChance;
    private Races race;
    private boolean dropable;
    private boolean sellable;
    private final ItemStack itemStack;
    private final ItemStack shopItemStack;
    private static HashMap<ItemStack, OneironItem> OIFromIS = new HashMap<>(); //OW = OneironItem; IS = ItemStack
    private Rarity rarity;

    /**
     * Creates a custom item
     *
     * @param value      The value in screws
     * @param itemStack
     * @param dropChance The dropchance from 1 (low) to 1000 (high)
     */
    public OneironItem(int value, ItemStack itemStack, int dropChance, Rarity rarity) {
        this.value = value;
        this.dropChance = dropChance;
        ItemMeta itemMeta = itemStack.getItemMeta();
        this.rarity = rarity;
        switch (rarity) {
            case COMMON:
                itemMeta.setDisplayName(ChatColor.GRAY + itemMeta.getDisplayName());
                itemMeta.setLore(new ArrayList<String>() {{
                    add(ChatColor.GRAY + "Common");
                }});
                break;
            case UNCOMMON:
                itemMeta.setDisplayName(ChatColor.DARK_BLUE + itemMeta.getDisplayName());
                itemMeta.setLore(new ArrayList<String>() {{
                    if(itemMeta.getLore() != null) {
                        addAll(itemMeta.getLore());
                        add(ChatColor.DARK_BLUE + "Uncommon");
                    } else {
                        add(ChatColor.DARK_BLUE + "Uncommon");
                    }
                }});
                break;
            case RARE:
                itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + itemMeta.getDisplayName());
                itemMeta.setLore(new ArrayList<String>() {{
                    if(itemMeta.getLore() != null) {
                        addAll(itemMeta.getLore());
                        add(ChatColor.LIGHT_PURPLE + "Rare");
                    } else {
                        add(ChatColor.LIGHT_PURPLE + "Rare");
                    }
                }});
                break;
            case LEGENDARY:
                itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + itemMeta.getDisplayName());
                itemMeta.setLore(new ArrayList<String>() {{
                    if(itemMeta.getLore() != null) {
                        addAll(itemMeta.getLore());
                        add(ChatColor.AQUA + "Legendary");
                    } else {
                        add(ChatColor.AQUA + "Legendary");
                    }
                }});
                break;
            case MYTHIC:
                itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + itemMeta.getDisplayName());
                itemMeta.setLore(new ArrayList<String>() {{
                    if(itemMeta.getLore() != null) {
                        addAll(itemMeta.getLore());
                        add(ChatColor.DARK_PURPLE + "Mythic");
                    } else {
                        add(ChatColor.DARK_PURPLE + "Mythic");
                    }
                }});
                break;
            case QUEST:
                itemMeta.setDisplayName(ChatColor.YELLOW + itemMeta.getDisplayName());
                itemMeta.setLore(new ArrayList<String>() {{
                    if(itemMeta.getLore() != null) {
                        addAll(itemMeta.getLore());
                        add(ChatColor.YELLOW + "Quest item");
                    } else {
                        add(ChatColor.YELLOW + "Quest item");
                    }
                }});
                dropable = false;
                sellable = false;
                break;
        }
        itemStack.setItemMeta(itemMeta);
        this.itemStack = itemStack;
        shopItemStack = Util.crItem(itemStack.getType(), itemStack.getAmount(), itemStack.getItemMeta().getDisplayName() + ChatColor.GREEN + " [" + value + "]", itemStack.getItemMeta().getLore());
        OIFromIS.put(itemStack, this);
    }

    public int getValue() {
        return value;
    }

    public Races getRace() {
        return race;
    }

    public boolean isDropable() {
        return dropable;
    }

    public boolean isSellable() {
        return sellable;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getDropChance() {
        return dropChance;
    }

    public ItemStack getShopItemStack() {
        return shopItemStack;
    }

    public void setRace(Races race) {
        this.race = race;
    }

    public void setDropable(boolean dropable) {
        this.dropable = dropable;
    }

    public void setSellable(boolean sellable) {
        this.sellable = sellable;
    }
}
