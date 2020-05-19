package de.kasyyy.oneiron.custommobs;

import de.kasyyy.oneiron.custommobs.mobs.ExchangeMerchant;
import de.kasyyy.oneiron.custommobs.mobs.ForestSpider;
import de.kasyyy.oneiron.custommobs.mobs.WeakSlime;
import de.kasyyy.oneiron.custommobs.mobs.WeakZombie;
import de.kasyyy.oneiron.items.OneironCurrency;
import de.kasyyy.oneiron.items.OneironItem;
import de.kasyyy.oneiron.items.armor.ArmorManager;
import de.kasyyy.oneiron.items.weapons.WeaponManager;

import java.util.ArrayList;

public class OneironMobManager {

    private static ArrayList<OneironItem> basicDrops = new ArrayList<OneironItem>() {{
        add(OneironCurrency.SCREW);
        add(OneironCurrency.SCRAP_METAL);
        add(WeaponManager.getInstance().getShepherdsStaff());
        add(ArmorManager.getInstance().leatherJacket);
        add(ArmorManager.getInstance().shinyPants);
    }};
    private static ArrayList<OneironItem> advancedDrops = new ArrayList<OneironItem>(){{addAll(basicDrops); add(WeaponManager.getInstance().getShepherdsStaff()); add(WeaponManager.getInstance().getGreatStaff());}};

    public static void loadOneironMobs() {
        //Create a template mob without id and entity here
        //To work correctly the name must be the same as the nms entities custom name
        OneironMobTemplate testMob = new OneironMobTemplate(WeakZombie.WEAK_ZOMBIE_NAME, 20, 10, 10, basicDrops, 5);
        OneironMobTemplate weakSlime = new OneironMobTemplate(WeakSlime.name, 100, 100, 20, basicDrops, 10);
        OneironMobTemplate forestSpider = new OneironMobTemplate(ForestSpider.NAME, 1000, 100, 30, advancedDrops, 15);
        OneironMobTemplate exchangeMerchant = new OneironMobTemplate(ExchangeMerchant.NAME, 1000, 10, 0, null, 0);
    }


}
