package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemExchange {
    public static void exchangeItems(Player p, ItemStack price, int priceAmount, ItemStack reward, int rewardAmount) {
        int rewardOriginal = reward.getAmount();
        reward.setAmount(rewardAmount);
        int amount = 0;
        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;
            if (item.isSimilar(price)) {

                amount += item.getAmount();
                p.sendMessage(Util.getDebug() + "Your needed items: " + amount);
            }
        }

        //Checks if the player has enough of the needed item
        if (amount >= priceAmount) {
            int left = priceAmount; //The number of needed items which need to be removed
            for (ItemStack itemStack : p.getInventory().getContents()) {
                if (left == 0) {
                    //If all needed items are removed the player gets the reward
                    p.getInventory().addItem(reward);
                    reward.setAmount(rewardOriginal);
                    break;
                }
                if (itemStack == null) continue;

                //If there is a needed item the amount of the itemstack is checked and subtracted from the total
                if (itemStack.isSimilar(price)) {
                    //Checks if the subtraction would return a negative result and if so stops at 0
                    int result = left - itemStack.getAmount();
                    if (left - itemStack.getAmount() >= 0) {
                        left -= itemStack.getAmount();
                        p.sendMessage(Util.getDebug() + "Number of needed items left: " + left);
                        itemStack.setAmount(0);
                        continue;
                    }
                    if (left - itemStack.getAmount() < 0) {
                        p.sendMessage(Util.getDebug() + "The itemstack " + itemStack.getAmount());
                        for (int i = left; i > 0; i--) {
                            itemStack.setAmount(itemStack.getAmount() - 1);
                            left--;
                            p.sendMessage(Util.getDebug() + "Loop executed " + i);
                        }
                    }


                }
            }

        } else {
            p.sendMessage(Util.getPrefix() + "Don't screw around!");
        }
    }
    public static int getMoney(Player p) {
        int amount = 0;
        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;
            if (item.isSimilar(OneironCurrency.SCREW.getItemStack())) {
                amount += item.getAmount();
                continue;
            }
            if(item.isSimilar(OneironCurrency.SCRAP_METAL.getItemStack())) {
                amount = amount + item.getAmount() * 64;
            }
        }
        p.sendMessage(Util.getDebug() + "You have " + amount  + " screws");
        return amount;
    }
    public static void buyItem(Player p, ItemStack toBuy, int prize) {
        p.sendMessage(Util.getDebug() + "Prize is " + prize + " , you have " + getMoney(p));
        if(getMoney(p) < prize) return;

        for(ItemStack itemStack : p.getInventory().getContents()) {
            if(prize <= 0) {
                p.sendMessage(Util.getDebug() + "Added bought item");
                p.getInventory().addItem(toBuy);
                if(prize < 0) {
                    p.sendMessage(Util.getDebug() + "You payed too much");
                    while(prize < 0) {
                        p.getInventory().addItem(OneironCurrency.SCREW.getItemStack());
                        prize++;
                    }
                }
                break;
            }
            if(itemStack == null) continue;
            if(itemStack.isSimilar(OneironCurrency.SCREW.getItemStack())) {
                p.sendMessage(Util.getDebug() + "Itemstack similar to screw");
                if (prize - itemStack.getAmount() > 0) {
                    prize -= itemStack.getAmount();
                    itemStack.setAmount(0);
                    continue;
                } else {
                    while (prize >= 0) {
                        itemStack.setAmount(itemStack.getAmount() - 1);
                        prize--;
                    }
                }
            }
            if(itemStack.isSimilar(OneironCurrency.SCRAP_METAL.getItemStack())) {
                p.sendMessage(Util.getDebug() + "Itemstack similar to scrap metal");
                if(prize - itemStack.getAmount() > 0) {
                    prize -= itemStack.getAmount() * 64;
                    itemStack.setAmount(0);
                    continue;
                } else {
                    while(prize >= 0) {
                        itemStack.setAmount(itemStack.getAmount()- 1);
                        prize -= 64;
                    }

                }
            }
        }
    }
}
