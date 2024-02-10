package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;
import pl.mn.mncustomenchants.main;

public class Quake implements Listener{

    public Quake(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }


    @EventHandler
    public void OnAttack (EntityDamageByEntityEvent event){

        Player sender;


        if (event.getDamager() instanceof Projectile && ((Projectile)event.getDamager()).getShooter() instanceof Player){
            sender = (Player)((Projectile)event.getDamager()).getShooter();
        } else if (event.getDamager() instanceof Player){
            sender = (Player) event.getDamager();
        } else {
            return;
        }


        if (!EntityUtils.isPlayerWithEnch(CustomEnchantments.quake, sender, EquipmentSlot.HAND)){ return; }



        if (!event.getEntity().isDead()) { return; }




        int quakeLvl = sender.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.quake);

        for (Entity e : event.getEntity().getNearbyEntities(5, 5, 5)){

            if (e instanceof LivingEntity && !(e == sender)){

                EntityUtils.DamageType damageType = event.getDamager() instanceof Projectile ? EntityUtils.DamageType.PROJECTILE : EntityUtils.DamageType.MELEE;

                CustomDamage.damage((LivingEntity) e, sender, quakeLvl * CustomDamage.getDamage((LivingEntity) e, sender, CustomDamage.getRawDamage(event, sender), damageType), damageType, true, event.getDamager());

                /*
                if (EntityUtils.isPlayerWithEnch(CustomEnchantments.decay, sender, EquipmentSlot.HAND)){
                    int enchLvl = sender.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.decay);
                    CustomEffects.decay((LivingEntity) e, 80, enchLvl);
                }

                if (EntityUtils.isPlayerWithEnch(CustomEnchantments.ice_aspect, sender, EquipmentSlot.HAND)){
                    int enchLvl = sender.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.ice_aspect);
                    CustomEffects.freeze((LivingEntity) e, 80, enchLvl);
                    ((Player) event.getDamager()).playSound(e, Sound.BLOCK_GLASS_BREAK, SoundCategory.HOSTILE, 0.5f, 2);

                }

                if (EntityUtils.isPlayerWithEnch(CustomEnchantments.thunder_aspect, sender, EquipmentSlot.HAND)){
                    int enchLvl = sender.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.thunder_aspect);

                    if (EntityUtils.getChance(10, enchLvl)){
                        CustomEffects.stun((LivingEntity) e, 35);
                    }
                }

                if (EntityUtils.isPlayerWithEnch(Enchantment.FIRE_ASPECT, sender, EquipmentSlot.HAND)){
                    int enchLvl = sender.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.FIRE_ASPECT);

                    e.setFireTicks(80 * enchLvl);
                }


                 */

            }
        }


    }



}
