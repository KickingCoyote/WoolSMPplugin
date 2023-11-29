package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.ApplyEffect;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;

public class Quake implements Listener {

    Plugin plugin;

    public Quake (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }


    @EventHandler
    public void OnAttack (EntityDamageByEntityEvent event){

        if (EntityClassifications.isPlayerWithEnch(CustomEnchantments.quake, event.getDamager(), EquipmentSlot.HAND)){

            if (((LivingEntity)event.getEntity()).getHealth() < event.getFinalDamage()){

                for (Entity e : event.getEntity().getNearbyEntities(3, 3, 3)){

                    if (e instanceof LivingEntity && !(e == event.getDamager())){

                        ((LivingEntity) e).damage(event.getDamage());
                        if (EntityClassifications.isPlayerWithEnch(CustomEnchantments.decay, event.getDamager(), EquipmentSlot.HAND)){
                            int enchLvl = ((Player)event.getDamager()).getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.decay);
                            ApplyEffect.Decay((LivingEntity) event.getEntity(), (40 / enchLvl), plugin);
                        }

                        if (EntityClassifications.isPlayerWithEnch(CustomEnchantments.thunder_aspect, event.getDamager(), EquipmentSlot.HAND)){
                            int enchLvl = ((Player)event.getDamager()).getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.thunder_aspect);

                            if (EntityClassifications.getChance(10, enchLvl)){
                                ApplyEffect.Stun((Mob) event.getEntity(), 100, plugin);
                            }
                        }

                        if (EntityClassifications.isPlayerWithEnch(Enchantment.FIRE_ASPECT, (Player)event.getDamager(), EquipmentSlot.HAND)){
                            int enchLvl = ((Player)event.getDamager()).getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.FIRE_ASPECT);
                            ((LivingEntity)event.getEntity()).setFireTicks(enchLvl * 80);
                        }

                    }
                }
            }
        }
    }



}
