package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.PassiveEffects.PassiveEffects;

public class Two_Handed {

    Plugin plugin;
    public Two_Handed (Plugin plugin){
        this.plugin = plugin;
    }

    public static void CheckTwoHanded (Player player){

        boolean isTwoHanded = EntityUtils.isPlayerWithEnch(CustomEnchantments.two_handed, player, EquipmentSlot.HAND);
        boolean hasOffHand = !player.getInventory().getItemInOffHand().isEmpty();

        if (isTwoHanded && hasOffHand && !(player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchantments.two_handed) == 0)){
            PassiveEffects.Curse(player, true);
        }
        else {
            PassiveEffects.Curse(player, false);
        }


    }


}
