package de.kasyyy.oneiron.items;

import de.kasyyy.oneiron.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemExchange {

    //This method should only be used for exchanging currency
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

    /**
     * Gets the total amount of currency in a players inventory
     * Screw = 1; Scrap metal = 64
     *
     * @param p
     * @return Returns the amount of currency
     */
    public static int getCurrency(Player p) {
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

    /**
     * //Allows a player to buy an item with their current amount of currency
     *
     * @param p
     * @param toBuy The itemstack to buy
     * @param prize
     */
    public static void buyItem(Player p, ItemStack toBuy, int prize) {
        p.sendMessage(Util.getDebug() + "Prize is " + prize + ", you have " + getCurrency(p));
        if (getCurrency(p) < prize) return;

        for(ItemStack itemStack : p.getInventory().getContents()) {
            if(prize <= 0) {
                //Adds the bought item
                p.getInventory().addItem(toBuy);
                if(prize < 0) {
                    //If the player payed too much, because he had a scrap metal, which wasn't converted the rest of the
                    //currency will be returned as screws
                    p.sendMessage(Util.getDebug() + "You payed too much");
                    while(prize < 0) {
                        p.getInventory().addItem(OneironCurrency.SCREW.getItemStack());
                        prize++;
                    }
                }
                break;
            }
            if(itemStack == null) continue;
            //Checks if the itemstack is a screw and if so subtracts from it
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
            //Checks if the itemstack is scrap metal and if so subtracts from it
            if(itemStack.isSimilar(OneironCurrency.SCRAP_METAL.getItemStack())) {
                p.sendMessage(Util.getDebug() + "Itemstack similar to scrap metal");
                if (prize - (itemStack.getAmount() * 64) > 0) {
                    prize -= itemStack.getAmount() * 64;
                    p.sendMessage(Util.getDebug() + "Prize equals " + prize);
                    itemStack.setAmount(0);
                } else {
                    while(prize >= 0) {
                        itemStack.setAmount(itemStack.getAmount()- 1);
                        prize -= 64;
                        p.sendMessage(Util.getDebug() + "Prize equals " + prize);
                    }

                }
            }
        }
    }
}
