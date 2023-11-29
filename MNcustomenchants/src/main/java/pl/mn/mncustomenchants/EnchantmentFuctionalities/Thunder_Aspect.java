package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.Bukkit;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.ApplyEffect;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;

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
        int nrn = random.nextInt(10);
        int enchLvl;



        if (EntityClassifications.isPlayerWithEnch(ench, event.getDamager(), EquipmentSlot.HAND)){

            enchLvl = ((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(ench);

            if (nrn < enchLvl)
            {
                //if pvp
                if(event.getEntity() instanceof Player) {


                }

                //if pve
                else if (event.getEntity() instanceof Mob){
                    Mob entity = (Mob) event.getEntity();

                    Location location = entity.getLocation();
                    location.offset(0, 1, 0);
                    //location.setPitch(0f);


                    entity.getWorld().spawnParticle(Particle.END_ROD, location, 30);


                    ApplyEffect.Stun(entity, 35, plugin);



                }
            }




        }

    }






}
