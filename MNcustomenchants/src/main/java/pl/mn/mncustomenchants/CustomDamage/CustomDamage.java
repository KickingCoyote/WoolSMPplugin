package pl.mn.mncustomenchants.CustomDamage;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;
import pl.mn.mncustomenchants.ItemMethods.AttributeType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.main;

public class CustomDamage implements Listener {

    Plugin plugin;
    //private static double finalDamage;

    public CustomDamage (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }


    @EventHandler
    public void DamageEvent(EntityDamageEvent event){

        //The Vanilla damage later turned into pre damage.
        double damage = event.getDamage();



        //Calculates the Pre Damage (The damage before calculating in the receivers damage reduction)
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK || event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
            damage = RunPreDamageOperations((EntityDamageByEntityEvent)event);
        }



        //sort damage into categories
        if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)){
            damageEntity((LivingEntity) event.getEntity(), damage, EntityClassifications.DamageType.PROJECTILE);
        }
        else if (event.getCause().equals(EntityDamageEvent.DamageCause.MAGIC) || event.getCause().equals(EntityDamageEvent.DamageCause.DRAGON_BREATH) || event.getCause().equals(EntityDamageEvent.DamageCause.WITHER) || event.getCause().equals(EntityDamageEvent.DamageCause.POISON)){
            damageEntity((LivingEntity) event.getEntity(), damage, EntityClassifications.DamageType.MAGIC);
        }
        else if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) || event.getCause().equals(EntityDamageEvent.DamageCause.THORNS) || event.getCause().equals(EntityDamageEvent.DamageCause.CONTACT)){
            damageEntity((LivingEntity) event.getEntity(), damage, EntityClassifications.DamageType.MELEE);
        }
        else if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || event.getCause().equals(EntityDamageEvent.DamageCause.LAVA) || event.getCause().equals(EntityDamageEvent.DamageCause.HOT_FLOOR) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)){
            damageEntity((LivingEntity) event.getEntity(), damage, EntityClassifications.DamageType.FIRE);
        }
        else if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL) || event.getCause().equals(EntityDamageEvent.DamageCause.FLY_INTO_WALL)){
            damageEntity((LivingEntity) event.getEntity(), damage, EntityClassifications.DamageType.FALLING);
        }
        else if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || event.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)){
            damageEntity((LivingEntity) event.getEntity(), damage, EntityClassifications.DamageType.BLAST);
        }
        else {
            damageEntity((LivingEntity) event.getEntity(), damage, EntityClassifications.DamageType.TRUE);
        }

        event.setDamage(0);



        //event.setCancelled(true);
    }




    public static double RunPreDamageOperations(EntityDamageByEntityEvent event){





        double damage = 0;



        //The person dealing damage
        LivingEntity sender;
        if (event.getDamager() instanceof Projectile){
            sender = (LivingEntity) ((Projectile) event.getDamager()).getShooter();
        } else {

            sender = (LivingEntity) event.getDamager();
        }




        //Thorns
        if (event.getEntity() instanceof Player && ItemUtils.getPlayerAttribute((Player) event.getEntity(), AttributeType.THORNS) != 0){
            damageEntity(sender, ItemUtils.getPlayerAttribute((Player) event.getEntity(), AttributeType.THORNS), EntityClassifications.DamageType.MELEE);

            //Play hurt sound if the entity doesn't die
            if (!sender.isDead()){
                EntityClassifications.playSound(sender.getLocation(), 30, sender.getHurtSound(), SoundCategory.HOSTILE, 1, 1);
                ((LivingEntity)event.getDamager()).playHurtAnimation(10);
            }
        }



        if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Player){

            //TODO: Snowballs may or may not do damage to players, check that.
            damage = ItemUtils.getPlayerAttribute((Player) sender, AttributeType.PROJECTILE_DAMAGE);

            double projSpeed = ItemUtils.getPlayerAttribute((Player) sender, AttributeType.PROJECTILE_SPEED);

            if (event.getDamager() instanceof Arrow){

                //A value between 0 and 1 based on the arrow velocity that determines the damage
                double bowCharge =  Math.min(3 * projSpeed, event.getDamager().getVelocity().length()) / (3 * projSpeed);
                //if bowCharge is above 0.9 round it to 1 for nicer numbers
                if (bowCharge > 0.9) {
                    bowCharge = 1;
                }

                damage *= bowCharge;
            }


        } else if (event.getDamager() instanceof Player){
            damage = ItemUtils.getPlayerAttribute((Player) event.getDamager(), AttributeType.ATTACK_DAMAGE);

            damage *= ((Player) event.getDamager()).getAttackCooldown();
            //If critical hit
            if (event.isCritical()){
                damage *= 1.5;
            }

        } else {
            damage = event.getDamage();
        }


        if (sender instanceof Player){
            int regicide = EntityClassifications.combinedEnchantLvl((Player) sender, CustomEnchantments.regicide);

            //regicide deals bonus damage to players
            if (event.getEntity() instanceof Player && regicide != 0){
                damage *= (1 + (0.1 * regicide));
            }
        }

        if (event.getEntity() instanceof Player){

            Player receiver = (Player)event.getEntity();

            if (receiver.isBlocking() && receiver.getLocation().getDirection().setY(0).angle(event.getDamager().getLocation().getDirection().setY(0).multiply(-1)) < 1.5708){

                damage = 0;
            }

        }


        return damage;
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

    //Calculates the damage on the receiver (The damage reduction on the receiver)
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
            if (entity instanceof Player){
                damageAfterArmor = damage * Math.pow(0.96, ItemUtils.getPlayerAttribute((Player) entity, AttributeType.ARMOR));
            } else {
                damageAfterArmor = damage * Math.pow(0.96, EntityClassifications.combinedAttributeLvl(entity, Attribute.GENERIC_ARMOR) + EntityClassifications.combinedAttributeLvl(entity, Attribute.GENERIC_ARMOR_TOUGHNESS));
            }
        }


        int resEffect = 0;
        if (entity.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
            resEffect = entity.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier();
        }


        //TODO: SecProtLvl might be doubled 1 time to much
        finalDamage = damageAfterArmor * Math.pow(0.96, 2 * secProtLvl + protLvl) * (1 - Math.min(1, resEffect * 0.2));
        return finalDamage;
    }
}
