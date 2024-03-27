package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.PassiveEffects.PassiveEffects;

public class Curse_of_Corruption {


    public static void CheckCoC (Player player){


        int cocLvl = EntityUtils.combinedEnchantLvl(player, CustomEnchantments.curse_of_corruption);

        if (cocLvl > 1){
            PassiveEffects.Curse(player, true);
        }
        else {
            PassiveEffects.Curse(player, false);
        }


    }

}
