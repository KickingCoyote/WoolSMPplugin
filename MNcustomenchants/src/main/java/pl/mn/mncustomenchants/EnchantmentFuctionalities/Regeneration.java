package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;

public class Regeneration {

    static Plugin plugin;


    public Regeneration(Plugin plugin){
        Regeneration.plugin = plugin;
    }

    public static void CheckRegeneration (Player player){

        boolean hasRegen = false;
        int enchLvl = EntityClassifications.combinedEnchantLvl(player, CustomEnchantments.regeneration);

        for (EquipmentSlot e : EquipmentSlot.values()){
            if (EntityClassifications.isPlayerWithEnch(CustomEnchantments.regeneration, player, e)){
                hasRegen = true;
            }
        }

        CustomEffects.regen(player, enchLvl, !hasRegen);

    }



}
