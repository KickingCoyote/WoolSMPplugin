package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantment;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;

public class Regeneration {



    public static void CheckRegeneration (Player player){

        boolean hasRegen = false;
        int enchLvl = EntityUtils.combinedEnchantLvl(player, CustomEnchantment.regeneration);

        for (EquipmentSlot e : EquipmentSlot.values()){
            if (EntityUtils.isPlayerWithEnchantment(CustomEnchantment.regeneration, player, e)){
                hasRegen = true;
            }
        }

        CustomEffects.regen(player, enchLvl, !hasRegen);

    }



}
