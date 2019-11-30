package de.kasyyy.oneiron.custommobs.mobs;

import de.kasyyy.oneiron.items.OneironCurrency;
import de.kasyyy.oneiron.main.Oneiron;
import de.kasyyy.oneiron.util.Util;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.metadata.FixedMetadataValue;

public class ExchangeMerchant extends EntityVillagerTrader {
    public static final String NAME = ChatColor.GREEN + "Merchant";

    public ExchangeMerchant(EntityTypes<? extends EntityVillagerTrader> entitytypes, World world) {
        super(EntityTypes.WANDERING_TRADER, world);
        this.setInvulnerable(true);
        this.setCustomName(new ChatMessage(NAME));
        this.setCustomNameVisible(true);
        this.getBukkitEntity().setMetadata(Util.SELL, new FixedMetadataValue(Oneiron.getInstance(), OneironCurrency.EXCHANGE_NAME));
        this.getBukkitEntity().setMetadata(Util.INVULNERABLE, new FixedMetadataValue(Oneiron.getInstance(), ""));
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0);
    }


}
