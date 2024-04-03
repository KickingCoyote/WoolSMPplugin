package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.PassiveEffects.PassiveEffects;

public class CurseEnchantments {


    //All enchantments that applies the Curse effect
    public static void CheckCurse (Player player){

        boolean isTwoHanded = EntityUtils.isPlayerWithEnch(CustomEnchantments.two_handed, player, EquipmentSlot.HAND);
        boolean hasOffHand = !(player.getInventory().getItemInOffHand().isEmpty() || EntityUtils.itemEnchLvl(CustomEnchantments.weightless, player.getInventory().getItemInOffHand()) > 0);


        int cocLvl = EntityUtils.combinedEnchantLvl(player, CustomEnchantments.curse_of_corruption);

        if (cocLvl > 1){
            PassiveEffects.Curse(player, true);
            return;
        }


        if (isTwoHanded && hasOffHand && !(player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchantments.two_handed) == 0)){

            PassiveEffects.Curse(player, true);
            return;
        }


        PassiveEffects.Curse(player, false);



    }


}
