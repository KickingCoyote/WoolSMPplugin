package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;

public class Decay implements Listener {

    Plugin plugin;


    public Decay(Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void OnAttacking(EntityDamageByEntityEvent event){

        Enchantment ench = CustomEnchantments.decay;

        int enchLvl;


        if(EntityClassifications.isPlayerWithEnch(ench, event.getDamager(), EquipmentSlot.HAND)){


            enchLvl = ((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(ench);


            if(event.getEntity() instanceof Player){

                LivingEntity entity = (LivingEntity) event.getEntity();

                CustomEffects.decay(entity, 80, enchLvl);

            }
            else {


                LivingEntity entity = (LivingEntity) event.getEntity();

                CustomEffects.decay(entity, 80, enchLvl);


            }


        }

    }

}
