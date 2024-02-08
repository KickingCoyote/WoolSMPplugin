package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;

public class Regeneration {



    public static void CheckRegeneration (Player player){

        boolean hasRegen = false;
        int enchLvl = EntityUtils.combinedEnchantLvl(player, CustomEnchantments.regeneration);

        for (EquipmentSlot e : EquipmentSlot.values()){
            if (EntityUtils.isPlayerWithEnch(CustomEnchantments.regeneration, player, e)){
                hasRegen = true;
            }
        }

        CustomEffects.regen(player, enchLvl, !hasRegen);

    }



}
