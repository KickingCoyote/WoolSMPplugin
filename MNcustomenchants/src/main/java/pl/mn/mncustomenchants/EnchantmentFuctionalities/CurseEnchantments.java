package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantment;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.PassiveEffects.PassiveEffects;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;

public class CurseEnchantments {


    //All enchantments that applies the Curse effect
    public static void CheckCurse (Player player){

        boolean isTwoHanded = EntityUtils.isPlayerWithEnchantment(CustomEnchantment.two_handed, player, EquipmentSlot.HAND);
        boolean hasOffHand = !(player.getInventory().getItemInOffHand().isEmpty() || EntityUtils.itemEnchantmentLvl(CustomEnchantment.weightless, player.getInventory().getItemInOffHand()) > 0);


        int cocLvl = EntityUtils.combinedEnchantLvl(player, CustomEnchantment.curse_of_corruption);

        int shatterLvl = 0;
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()){
            ItemStack i = player.getEquipment().getItem(equipmentSlot);
            if (i.hasItemMeta()){
                shatterLvl += ItemUtils.getShattered(i);
            }
        }

        if (shatterLvl > 0){
            PassiveEffects.Curse(player, true);
            return;
        }

        if (cocLvl > 1){
            PassiveEffects.Curse(player, true);
            return;
        }


        if (isTwoHanded && hasOffHand && EntityUtils.itemEnchantmentLvl(CustomEnchantment.two_handed, player.getInventory().getItemInMainHand()) != 0){

            PassiveEffects.Curse(player, true);
            return;
        }


        PassiveEffects.Curse(player, false);



    }


}
