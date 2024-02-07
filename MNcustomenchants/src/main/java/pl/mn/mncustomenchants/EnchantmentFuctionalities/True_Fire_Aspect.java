package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;
import pl.mn.mncustomenchants.main;


//Handles both fire aspect and inferno
public class True_Fire_Aspect implements Listener {

    public True_Fire_Aspect(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }


    @EventHandler
    public void OnAttack (EntityDamageByEntityEvent event){

        if (!EntityClassifications.isPlayerWithEnch(CustomEnchantments.true_fire_aspect, event.getDamager(), EquipmentSlot.HAND)){ return; }

        int enchLvl = ((Player) event.getDamager()).getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.true_fire_aspect);
        int infernoLvl = EntityClassifications.combinedEnchantLvl((Player)event.getDamager(), CustomEnchantments.inferno);


        CustomEffects.burn((LivingEntity) event.getEntity(), 80 * enchLvl, infernoLvl);


    }




}
