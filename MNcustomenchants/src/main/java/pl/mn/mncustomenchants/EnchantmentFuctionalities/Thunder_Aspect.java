package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.checkerframework.checker.units.qual.C;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.ApplyEffect;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;
import pl.mn.mncustomenchants.Particles.Particles;

import javax.management.AttributeNotFoundException;
import java.util.Random;

public class Thunder_Aspect implements Listener {

    Plugin plugin;
    public Thunder_Aspect (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void OnAttacking(EntityDamageByEntityEvent event){

        Enchantment ench = CustomEnchantments.thunder_aspect;

        Random random = new Random();
        int nrn = random.nextInt(20);
        int enchLvl;


        if (EntityClassifications.isPlayerWithEnch(ench, event.getDamager(), EquipmentSlot.HAND) && ((Player)event.getDamager()).getAttackCooldown() == 1){

            enchLvl = ((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(ench);



            ((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta().addItemFlags();

            LivingEntity entity = (LivingEntity) event.getEntity();
            //if pvp
            if(event.getEntity() instanceof Player && nrn < enchLvl) {

                CustomEffects.stun(entity, 35);
                //ApplyEffect.Stun(entity, 35, plugin);

            }

            //if pve
            else if (event.getEntity() instanceof Mob && nrn < enchLvl * 2){





                //entity.getWorld().spawnParticle(Particle.END_ROD, location, 30);


                //ApplyEffect.Stun(entity, 35, plugin);
                CustomEffects.stun(entity, 35);


            }





        }

    }

    @EventHandler
    public void d(){}






}
