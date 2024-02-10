package pl.mn.mncustomenchants.CustomDamage;

import org.bukkit.Bukkit;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.*;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.ItemMethods.AttributeType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;

public class CustomDamage implements Listener {

    Plugin plugin;
    //private static double finalDamage;

    public CustomDamage (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }


    @EventHandler
    public void DamageEvent(EntityDamageEvent event){

        if (!(event.getEntity() instanceof LivingEntity)){

            return;
        }


        //The Vanilla damage later turned into pre damage.
        double damage = event.getDamage();



        if (event instanceof EntityDamageByEntityEvent){


            LivingEntity eSender = getLivingEntitySender((EntityDamageByEntityEvent) event);

            //Blocking and Thorns
            if (event.getEntity() instanceof Player){


                Player receiver = (Player)event.getEntity();

                if (receiver.isBlocking() && receiver.getLocation().getDirection().setY(0).angle(((EntityDamageByEntityEvent)event).getDamager().getLocation().getDirection().setY(0).multiply(-1)) < 1.5708){

                    event.setDamage(0);
                    return;

                }


                if (ItemUtils.getPlayerAttribute(receiver, AttributeType.THORNS) > 0 && eSender != null){

                    damage(eSender, receiver, ItemUtils.getPlayerAttribute(receiver, AttributeType.THORNS), EntityUtils.DamageType.MELEE);

                }

            }




            if (((EntityDamageByEntityEvent) event).getDamager() instanceof Projectile && eSender instanceof Player){
                Player sender = (Player) eSender;

                damage((LivingEntity) event.getEntity(), sender, getRawDamage((EntityDamageByEntityEvent) event, sender), EntityUtils.DamageType.MELEE, true, ((EntityDamageByEntityEvent) event).getDamager());

                event.setDamage(0);
                return;

            } else if (eSender instanceof Player){

                Player sender = (Player) eSender;

                damage((LivingEntity) event.getEntity(), sender, getRawDamage((EntityDamageByEntityEvent) event, sender), EntityUtils.DamageType.MELEE, true, ((EntityDamageByEntityEvent) event).getDamager());

                event.setDamage(0);
                return;
            }


        }




        //sort damage into categories
        if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)){
            damage((LivingEntity) event.getEntity(), null, damage, EntityUtils.DamageType.PROJECTILE);
        }
        else if (event.getCause().equals(EntityDamageEvent.DamageCause.MAGIC) || event.getCause().equals(EntityDamageEvent.DamageCause.DRAGON_BREATH) || event.getCause().equals(EntityDamageEvent.DamageCause.WITHER) || event.getCause().equals(EntityDamageEvent.DamageCause.POISON)){
            damage((LivingEntity) event.getEntity(), null, damage, EntityUtils.DamageType.MAGIC);
        }
        else if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) || event.getCause().equals(EntityDamageEvent.DamageCause.THORNS) || event.getCause().equals(EntityDamageEvent.DamageCause.CONTACT)){
            damage((LivingEntity) event.getEntity(), null, damage, EntityUtils.DamageType.MELEE);
        }
        else if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || event.getCause().equals(EntityDamageEvent.DamageCause.LAVA) || event.getCause().equals(EntityDamageEvent.DamageCause.HOT_FLOOR) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)){
            damage((LivingEntity) event.getEntity(), null, damage, EntityUtils.DamageType.FIRE);
        }
        else if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL) || event.getCause().equals(EntityDamageEvent.DamageCause.FLY_INTO_WALL)){
            damage((LivingEntity) event.getEntity(), null, damage, EntityUtils.DamageType.FALLING);
        }
        else if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || event.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)){
            damage((LivingEntity) event.getEntity(), null, damage, EntityUtils.DamageType.BLAST);
        }
        else {
            damage((LivingEntity) event.getEntity(), null, damage, EntityUtils.DamageType.TRUE);
        }

        event.setDamage(0);



        //event.setCancelled(true);
    }


    //public static double Entity

    public static double getRawDamage(EntityDamageByEntityEvent event, Player sender){

        double damage = 0;

        //Melee
        if(event.getDamager() instanceof Player){

            damage = ItemUtils.getPlayerAttribute((Player) event.getDamager(), AttributeType.ATTACK_DAMAGE);

            damage *= ((Player) event.getDamager()).getAttackCooldown();
            //If critical hit
            if (event.isCritical()){
                damage *= 1.5;
            }
        }
        //Ranged
        if(event.getDamager() instanceof Projectile){

            damage = ItemUtils.getPlayerAttribute(sender, AttributeType.PROJECTILE_DAMAGE);

            double projSpeed = ItemUtils.getPlayerAttribute(sender, AttributeType.PROJECTILE_SPEED);

            if (event.getDamager() instanceof Arrow){

                damage *= EntityUtils.bowCharge(sender, (Projectile) event.getDamager());

            }

            if (event.getDamager() instanceof Firework){


                damage *= Math.min(1, 1.5 - (event.getEntity().getLocation().distance(event.getDamager().getLocation()) / 5));

            }

        }


        return damage;
    }


    private static LivingEntity getLivingEntitySender(EntityDamageByEntityEvent event) {
        LivingEntity eSender = null;

        if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof LivingEntity){
            eSender = (LivingEntity) ((Projectile) event.getDamager()).getShooter();
        }
        else if (event.getDamager() instanceof LivingEntity){
            eSender = (LivingEntity) event.getDamager();
        }
        return eSender;
    }


    /*
    public static double RunPreDamageOperations(EntityDamageByEntityEvent event){



        //Fully Cancels the event if the weapon is on cooldown
        //Only for melee
        if (event.getDamager() instanceof Player && ((Player) event.getDamager()).getCooldown(((Player)event.getDamager()).getInventory().getItemInMainHand().getType()) != 0) {
            event.setCancelled(true);
            return 0;
        }



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
            damage(sender, (Player)event.getEntity(), ItemUtils.getPlayerAttribute((Player) event.getEntity(), AttributeType.THORNS), EntityUtils.DamageType.MELEE);

            //Play hurt sound if the entity doesn't die
            if (!sender.isDead()){
                EntityUtils.playSound(sender.getLocation(), 30, sender.getHurtSound(), SoundCategory.HOSTILE, 1, 1);
                ((LivingEntity)event.getDamager()).playHurtAnimation(10);
            }
        }


        //If the damage was ranged and dealt by a player
        if (event.getDamager() instanceof Projectile && sender instanceof Player){


           // ((LivingEntity)event.getDamager()).playHurtAnimation(10);


            damage = ItemUtils.getPlayerAttribute((Player) sender, AttributeType.PROJECTILE_DAMAGE);

            double projSpeed = ItemUtils.getPlayerAttribute((Player) sender, AttributeType.PROJECTILE_SPEED);

            if (event.getDamager() instanceof Arrow){

                damage *= EntityUtils.bowCharge((Player) sender, (Projectile) event.getDamager());

            }

            if (event.getDamager() instanceof Firework){


                damage *= Math.min(1, 1.5 - (event.getEntity().getLocation().distance(event.getDamager().getLocation()) / 5));

            }


        }
        //If the damage was melee and dealt by a player
        else if (sender instanceof Player){
            damage = ItemUtils.getPlayerAttribute((Player) event.getDamager(), AttributeType.ATTACK_DAMAGE);

            damage *= ((Player) event.getDamager()).getAttackCooldown();
            //If critical hit
            if (event.isCritical()){
                damage *= 1.5;
            }

        }
        //If the direct / indirect sender wasn't a player set the damage to vanilla damage
        else {
            damage = event.getDamage();
        }






        //Blocking
        if (event.getEntity() instanceof Player){

            Player receiver = (Player)event.getEntity();

            if (receiver.isBlocking() && receiver.getLocation().getDirection().setY(0).angle(event.getDamager().getLocation().getDirection().setY(0).multiply(-1)) < 1.5708){

                damage = 0;
            }

        }


        return damage;
    }


     */

    /*
    //Returns modified damage but also does other calculations like thunder/fire/ice aspect
    private static double playerSpecificDamageEvents (Player player, Double damage, EntityDamageByEntityEvent event){

        int regicide = EntityUtils.combinedEnchantLvl(player, CustomEnchantments.regicide);


        //Checks if effects such as Thunder Aspect and Decay should be applied
        AttackEffectEnchantments.CheckAttackEffects(event, player);

        //regicide deals bonus damage to players
        if (event.getEntity() instanceof Player && regicide != 0){
            damage *= (1 + (0.1 * regicide));
        }




        return 0;
    }
    */

    public static void damage(LivingEntity target, LivingEntity damager, double damage, EntityUtils.DamageType damageType){

        damage(target, damager, damage, damageType, false, null);

    }


    public static void damage(LivingEntity target, LivingEntity damager, double damage, EntityUtils.DamageType damageType, boolean applyEffects, Entity directDamager){

        damage = getDamage(target, damager, damage, damageType);

        target.playHurtAnimation(10);

        //Apply weapon effects
        if (applyEffects && damager instanceof Player){
            AttackEffectEnchantments.CheckAttackEffects(target, (Player) damager, directDamager);
        }



        //Apply damage
        if (target.getHealth() > damage){
            target.setHealth(target.getHealth() - damage);
        } else {
            target.setHealth(0);
        }

    }

    public static double getDamage(LivingEntity target, LivingEntity damager, double damage, EntityUtils.DamageType damageType){



        int protLvl = 0;
        int secProtLvl = 0;



        //Attribute damage buffs
        if (damager instanceof Player){

            if (damageType == EntityUtils.DamageType.MELEE){

                damage += ItemUtils.getPlayerAttribute((Player) damager, AttributeType.ATTACK_DAMAGE, ItemUtils.AttributeOperator.ADD);
                damage *= ItemUtils.getPlayerAttribute((Player) damager, AttributeType.ATTACK_DAMAGE, ItemUtils.AttributeOperator.ADD_PROCENT);

            } else if (damageType == EntityUtils.DamageType.PROJECTILE) {
                damage += ItemUtils.getPlayerAttribute((Player) damager, AttributeType.PROJECTILE_DAMAGE, ItemUtils.AttributeOperator.ADD);
                damage *= ItemUtils.getPlayerAttribute((Player) damager, AttributeType.PROJECTILE_DAMAGE, ItemUtils.AttributeOperator.ADD_PROCENT);

            } else if (damageType == EntityUtils.DamageType.MAGIC){
                damage += ItemUtils.getPlayerAttribute((Player) damager, AttributeType.MAGIC_DAMAGE, ItemUtils.AttributeOperator.ADD);
                damage *= ItemUtils.getPlayerAttribute((Player) damager, AttributeType.MAGIC_DAMAGE, ItemUtils.AttributeOperator.ADD_PROCENT);

            } else if (damageType == EntityUtils.DamageType.FIRE){


            } else if (damageType == EntityUtils.DamageType.FALLING){


            } else if (damageType == EntityUtils.DamageType.BLAST){


            }
        }


        //Protection
        if (target instanceof Player){

            if (damageType == EntityUtils.DamageType.MAGIC){
                secProtLvl = EntityUtils.combinedEnchantLvl((Player) target, CustomEnchantments.magic_protection);


            } else if (damageType == EntityUtils.DamageType.PROJECTILE) {
                secProtLvl = EntityUtils.combinedEnchantLvl((Player) target, Enchantment.PROTECTION_PROJECTILE);


            } else if (damageType == EntityUtils.DamageType.FIRE){
                secProtLvl = EntityUtils.combinedEnchantLvl((Player) target, Enchantment.PROTECTION_FIRE);


            } else if (damageType == EntityUtils.DamageType.MELEE){
                secProtLvl = EntityUtils.combinedEnchantLvl((Player) target, CustomEnchantments.melee_protection);

            } else if (damageType == EntityUtils.DamageType.FALLING){
                //multiplied with 2 for balancing
                secProtLvl = 2 * EntityUtils.combinedEnchantLvl((Player) target, Enchantment.PROTECTION_FALL);

            } else if (damageType == EntityUtils.DamageType.BLAST){
                secProtLvl = EntityUtils.combinedEnchantLvl((Player) target, Enchantment.PROTECTION_EXPLOSIONS);

            }

            protLvl = EntityUtils.combinedEnchantLvl((Player) target, Enchantment.PROTECTION_ENVIRONMENTAL);

            damage *= Math.pow(0.96, (2 * secProtLvl) + protLvl);
        }



        //Armor
        if (damageType == EntityUtils.DamageType.PROJECTILE || damageType == EntityUtils.DamageType.MAGIC || damageType == EntityUtils.DamageType.MELEE || damageType == EntityUtils.DamageType.BLAST || damageType == EntityUtils.DamageType.FIRE){
            if (target instanceof Player){
                damage *= Math.pow(0.96, ItemUtils.getPlayerAttribute((Player) target, AttributeType.ARMOR));
            } else {
                damage *= Math.pow(0.96, EntityUtils.combinedAttributeLvl(target, Attribute.GENERIC_ARMOR) + EntityUtils.combinedAttributeLvl(target, Attribute.GENERIC_ARMOR_TOUGHNESS));
            }
        }

        //Resistance
        int resEffect = 0;
        if (target.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
            resEffect = target.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier();
        }

        damage *= (1 - Math.min(1, resEffect * 0.2));



        return damage;


    }



    /*
    public static void damageEntity(LivingEntity entity, double damage, EntityUtils.DamageType damageType){

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
    public static double calculateFinalDamage (LivingEntity entity, double damage, EntityUtils.DamageType damageType){
        double damageAfterArmor = damage;



        double protLvl = 0;
        double secProtLvl = 0;

        double finalDamage;

        if (entity instanceof Player){

            if (damageType == EntityUtils.DamageType.MAGIC){
                secProtLvl = EntityUtils.combinedEnchantLvl((Player) entity, CustomEnchantments.magic_protection);


            } else if (damageType == EntityUtils.DamageType.PROJECTILE) {
                secProtLvl = EntityUtils.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_PROJECTILE);


            } else if (damageType == EntityUtils.DamageType.FIRE){
                secProtLvl = EntityUtils.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_FIRE);


            } else if (damageType == EntityUtils.DamageType.MELEE){
                secProtLvl = EntityUtils.combinedEnchantLvl((Player) entity, CustomEnchantments.melee_protection);

            } else if (damageType == EntityUtils.DamageType.FALLING){
                //
                secProtLvl = 2 * EntityUtils.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_FALL);

            } else if (damageType == EntityUtils.DamageType.BLAST){
                secProtLvl = EntityUtils.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_EXPLOSIONS);

            }

            protLvl = EntityUtils.combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_ENVIRONMENTAL);
        }



        //Damage Calculations
        if (damageType == EntityUtils.DamageType.PROJECTILE || damageType == EntityUtils.DamageType.MAGIC || damageType == EntityUtils.DamageType.MELEE || damageType == EntityUtils.DamageType.BLAST || damageType == EntityUtils.DamageType.FIRE){
            if (entity instanceof Player){
                damageAfterArmor = damage * Math.pow(0.96, ItemUtils.getPlayerAttribute((Player) entity, AttributeType.ARMOR));
            } else {
                damageAfterArmor = damage * Math.pow(0.96, EntityUtils.combinedAttributeLvl(entity, Attribute.GENERIC_ARMOR) + EntityUtils.combinedAttributeLvl(entity, Attribute.GENERIC_ARMOR_TOUGHNESS));
            }
        }


        int resEffect = 0;
        if (entity.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
            resEffect = entity.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier();
        }


        finalDamage = damageAfterArmor * Math.pow(0.96, 2 * secProtLvl + protLvl) * (1 - Math.min(1, resEffect * 0.2));
        return finalDamage;
    }



     */
}
