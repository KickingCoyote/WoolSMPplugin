package pl.mn.mncustomenchants.CustomDamage;

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;

public class CustomDamage implements Listener {

    Plugin plugin;
    public CustomDamage (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    /*
    @EventHandler
    public void PreDamage (EntityDamageByEntityEvent event){


        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity){
            Bukkit.getPlayer("MN_128").sendMessage("1");
            damageEntity((LivingEntity) event.getEntity(), event.getDamage(), EntityClassifications.DamageType.MELEE);
            event.setDamage(0);
            Bukkit.getPlayer("MN_128").sendMessage("2");

        }
    } */

    @EventHandler
    public void DamageEvent(EntityDamageEvent event){



        if (event.getEntity() instanceof Player){
            if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)){
                damageEntity((LivingEntity) event.getEntity(), event.getDamage(), EntityClassifications.DamageType.PROJECTILE);
            }
            else if (event.getCause().equals(EntityDamageEvent.DamageCause.MAGIC) || event.getCause().equals(EntityDamageEvent.DamageCause.DRAGON_BREATH) || event.getCause().equals(EntityDamageEvent.DamageCause.WITHER) || event.getCause().equals(EntityDamageEvent.DamageCause.POISON)){
                damageEntity((LivingEntity) event.getEntity(), event.getDamage(), EntityClassifications.DamageType.MAGIC);
            }
            else if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) || event.getCause().equals(EntityDamageEvent.DamageCause.THORNS) || event.getCause().equals(EntityDamageEvent.DamageCause.CONTACT)){
                damageEntity((LivingEntity) event.getEntity(), event.getDamage(), EntityClassifications.DamageType.MELEE);
            }
            else if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || event.getCause().equals(EntityDamageEvent.DamageCause.LAVA) || event.getCause().equals(EntityDamageEvent.DamageCause.HOT_FLOOR) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)){
                damageEntity((LivingEntity) event.getEntity(), event.getDamage(), EntityClassifications.DamageType.FIRE);
            }
            else if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL) || event.getCause().equals(EntityDamageEvent.DamageCause.FLY_INTO_WALL)){
                damageEntity((LivingEntity) event.getEntity(), event.getDamage(), EntityClassifications.DamageType.FALLING);
            }
            else if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || event.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)){
                damageEntity((LivingEntity) event.getEntity(), event.getDamage(), EntityClassifications.DamageType.BLAST);
            }
            else {
                damageEntity((LivingEntity) event.getEntity(), event.getDamage(), EntityClassifications.DamageType.TRUE);
            }
            event.setDamage(0);
        }
    }


    public static void damageEntity(LivingEntity entity, double damage, EntityClassifications.DamageType damageType){

        //IGNORES IFRAMES
        double damageAfterArmor = damage;


        double protLvl = 0;
        double secProtLvl = 0;

        double finalDamage = 0;


        if(damageType == EntityClassifications.DamageType.TRUE){
            if (entity instanceof Player){
                protLvl = EntityClassifications.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_ENVIRONMENTAL);
            }

        } else if (damageType == EntityClassifications.DamageType.MAGIC){

            if(entity instanceof Player){
                protLvl = EntityClassifications.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_ENVIRONMENTAL);
                secProtLvl = EntityClassifications.combinedEnchantLvl((Player) entity, CustomEnchantments.magic_protection);
            }

        } else if (damageType == EntityClassifications.DamageType.PROJECTILE) {
            if(entity instanceof Player){
                protLvl = EntityClassifications.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_ENVIRONMENTAL);
                secProtLvl = EntityClassifications.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_PROJECTILE);
            }

        } else if (damageType == EntityClassifications.DamageType.FIRE){
            if(entity instanceof Player){
                protLvl = EntityClassifications.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_ENVIRONMENTAL);
                secProtLvl = EntityClassifications.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_FIRE);

            }

        } else if (damageType == EntityClassifications.DamageType.MELEE){
            if(entity instanceof Player){
                protLvl = EntityClassifications.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_ENVIRONMENTAL);
                secProtLvl = 0;/*EntityClassifications.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_FIRE);*/

            }
        }


        //Damage Calculations
        if (damageType == EntityClassifications.DamageType.PROJECTILE || damageType == EntityClassifications.DamageType.MAGIC || damageType == EntityClassifications.DamageType.MELEE){
            damageAfterArmor = damage * Math.pow(0.96, EntityClassifications.combinedAttributeLvl(entity, Attribute.GENERIC_ARMOR) + EntityClassifications.combinedAttributeLvl(entity, Attribute.GENERIC_ARMOR_TOUGHNESS));
        }

        finalDamage = damageAfterArmor * Math.pow(0.96, 2 * secProtLvl + protLvl);

        //Apply damage
        if (entity.getHealth() > finalDamage){
            entity.setHealth(entity.getHealth() - finalDamage);
        } else {
            entity.damage(10000);
        }


    }
}
