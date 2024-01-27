package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;

import java.util.Random;

public class Ice_Aspect implements Listener {

    Plugin plugin;
    public Ice_Aspect (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }


    @EventHandler
    public void OnAttacking(EntityDamageByEntityEvent event){

        Enchantment ench = CustomEnchantments.ice_aspect;


        if (EntityClassifications.isPlayerWithEnch(ench, event.getDamager(), EquipmentSlot.HAND) && ((Player)event.getDamager()).getAttackCooldown() == 1){


            int enchLvl = ((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(ench);



            ((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta().addItemFlags();

            LivingEntity entity = (LivingEntity) event.getEntity();
            //if pvp
            if(event.getEntity() instanceof Player) {

                CustomEffects.freeze(entity, 80, enchLvl);

            }

            //if pve
            else if (event.getEntity() instanceof Mob){

                CustomEffects.freeze(entity, 80, enchLvl);
            }





        }



    }
}
