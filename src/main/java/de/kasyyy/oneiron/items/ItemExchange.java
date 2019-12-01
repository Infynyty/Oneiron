package de.kasyyy.oneiron.items;

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
}
