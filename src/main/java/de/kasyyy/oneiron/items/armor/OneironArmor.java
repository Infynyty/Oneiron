package de.kasyyy.oneiron.items.armor;

import de.kasyyy.oneiron.items.OneironItem;
import de.kasyyy.oneiron.items.Rarity;
import de.kasyyy.oneiron.items.StatAlteringItem;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.player.OneironPlayer;
import de.kasyyy.oneiron.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class OneironArmor extends StatAlteringItem {

    private static HashMap<ItemStack, OneironArmor> oAFromIS = new HashMap<>();

    public OneironArmor(int value, ItemStack itemStack, int dropChance, Rarity rarity, int addedHealth, int addedMana, int addedSpeed, int addedDefense, int addedDamage) {
        super(value, itemStack, dropChance, rarity, addedHealth, addedMana, addedSpeed, addedDefense, addedDamage);
        oAFromIS.putIfAbsent(itemStack, this);
    }

    public static ArrayList<OneironArmor> getAllArmorPieces() {
        ArrayList<OneironArmor> allArmorPieces = new ArrayList<>();
        try(Connection connection = Oneiron.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM OneironArmor;")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                final OneironArmor oneironItem = new OneironArmor(
                        resultSet.getInt("value"),
                        Util.crItem(
                                Material.valueOf(resultSet.getString("itemStack")),
                                1, resultSet.getString("name"), Collections.singletonList(resultSet.getString("lore"))
                        ),
                        resultSet.getInt("dropChance"),
                        Rarity.valueOf(resultSet.getString("rarity")),
                        resultSet.getInt("addedHealth"),
                        resultSet.getInt("addedMana"),
                        resultSet.getInt("addedDamage"),
                        resultSet.getInt("addedSpeed"),
                        resultSet.getInt("addedDefense")
                );
                allArmorPieces.add(oneironItem);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allArmorPieces;
    }

    public static HashMap<ItemStack, OneironArmor> getoAFromIS() {
        return oAFromIS;
    }


}





