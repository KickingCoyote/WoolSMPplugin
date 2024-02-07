package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;

public class Quake implements Listener {

    Plugin plugin;

    public Quake (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }


    @EventHandler
    public void OnAttack (EntityDamageByEntityEvent event){



        if (!EntityClassifications.isPlayerWithEnch(CustomEnchantments.quake, event.getDamager(), EquipmentSlot.HAND)){ return; }



        if (!((event.getEntity()).isDead())) { return; }

        Player damager = (Player) event.getDamager();

        int quakeLvl = damager.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.quake);

        for (Entity e : event.getEntity().getNearbyEntities(5, 5, 5)){

            if (e instanceof LivingEntity && !(e == event.getDamager())){

                CustomDamage.damageEntity((LivingEntity) e, quakeLvl * CustomDamage.RunPreDamageOperations(event), EntityClassifications.DamageType.MELEE);

                if (EntityClassifications.isPlayerWithEnch(CustomEnchantments.decay, damager, EquipmentSlot.HAND)){
                    int enchLvl = damager.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.decay);
                    CustomEffects.decay((LivingEntity) e, 80, enchLvl);
                }

                if (EntityClassifications.isPlayerWithEnch(CustomEnchantments.ice_aspect, damager, EquipmentSlot.HAND)){
                    int enchLvl = damager.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.ice_aspect);
                    CustomEffects.freeze((LivingEntity) e, 80, enchLvl);
                    ((Player) event.getDamager()).playSound(e, Sound.BLOCK_GLASS_BREAK, SoundCategory.HOSTILE, 0.5f, 2);

                }

                if (EntityClassifications.isPlayerWithEnch(CustomEnchantments.thunder_aspect, damager, EquipmentSlot.HAND)){
                    int enchLvl = damager.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.thunder_aspect);

                    if (EntityClassifications.getChance(10, enchLvl)){
                        CustomEffects.stun((LivingEntity) e, 35);
                    }
                }

                if (EntityClassifications.isPlayerWithEnch(Enchantment.FIRE_ASPECT, damager, EquipmentSlot.HAND)){
                    int enchLvl = damager.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.FIRE_ASPECT);

                    e.setFireTicks(80 * enchLvl);
                }


            }
        }


    }



}
