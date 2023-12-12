package pl.mn.mncustomenchants.CustomDamage;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;

public class CustomDamage implements Listener {

    Plugin plugin;
    //private static double finalDamage;

    public CustomDamage (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }


    @EventHandler
    public void DamageEvent(EntityDamageEvent event){


        if (event.getEntity() instanceof Player && !((Player)event.getEntity()).isBlocking()){
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

    @EventHandler
    public void onDeath (PlayerDeathEvent event){

    }


    public static void damageEntity(LivingEntity entity, double damage, EntityClassifications.DamageType damageType){

        //IGNORES IFRAMES
        double finalDamage = calculateFinalDamage(entity, damage, damageType);

        //Apply damage
        if (entity.getHealth() > finalDamage){
            entity.setHealth(entity.getHealth() - finalDamage);
        } else {
            entity.setHealth(0);
        }


    }
    public static double calculateFinalDamage (LivingEntity entity, double damage, EntityClassifications.DamageType damageType){
        double damageAfterArmor = damage;



        double protLvl = 0;
        double secProtLvl = 0;

        double finalDamage;

        if (entity instanceof Player){

            if (damageType == EntityClassifications.DamageType.MAGIC){
                secProtLvl = EntityClassifications.combinedEnchantLvl((Player) entity, CustomEnchantments.magic_protection);


            } else if (damageType == EntityClassifications.DamageType.PROJECTILE) {
                secProtLvl = EntityClassifications.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_PROJECTILE);


            } else if (damageType == EntityClassifications.DamageType.FIRE){
                secProtLvl = EntityClassifications.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_FIRE);


            } else if (damageType == EntityClassifications.DamageType.MELEE){
                secProtLvl = EntityClassifications.combinedEnchantLvl((Player) entity, CustomEnchantments.melee_protection);

            } else if (damageType == EntityClassifications.DamageType.FALLING){
                secProtLvl = 2 * EntityClassifications.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_FALL);
            }
            protLvl = EntityClassifications.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_ENVIRONMENTAL);
        }



        //Damage Calculations
        if (damageType == EntityClassifications.DamageType.PROJECTILE || damageType == EntityClassifications.DamageType.MAGIC || damageType == EntityClassifications.DamageType.MELEE || damageType == EntityClassifications.DamageType.BLAST || damageType == EntityClassifications.DamageType.FIRE){
            damageAfterArmor = damage * Math.pow(0.96, EntityClassifications.combinedAttributeLvl(entity, Attribute.GENERIC_ARMOR) + EntityClassifications.combinedAttributeLvl(entity, Attribute.GENERIC_ARMOR_TOUGHNESS));
        }


        int resEffect = 0;
        if (entity.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
            resEffect = entity.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier();
        }


        finalDamage = damageAfterArmor * Math.pow(0.96, 2 * secProtLvl + protLvl) * (1 - Math.min(1, resEffect * 0.2));
        return finalDamage;
    }
}
