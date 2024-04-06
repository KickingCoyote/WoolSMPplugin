package pl.mn.mncustomenchants.CustomDamage;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.*;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.ItemMethods.AttributeType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.ItemMethods.Keys;

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

                //Blocking
                if (receiver.isBlocking() && receiver.getLocation().getDirection().setY(0).angle(((EntityDamageByEntityEvent)event).getDamager().getLocation().getDirection().setY(0).multiply(-1)) < 1.5708){
                    event.setDamage(0);
                    return;
                }

                //Thorns
                if (ItemUtils.getEntityAttribute(receiver, AttributeType.THORNS) > 0 && eSender != null){
                    damage(eSender, receiver, ItemUtils.getEntityAttribute(receiver, AttributeType.THORNS), EntityUtils.DamageType.MELEE);
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


            //Damage by entities
            else {

                //Projectile shoot by Entity
                if (((EntityDamageByEntityEvent) event).getDamager() instanceof Projectile){

                    //Not a dispenser
                    if (((Projectile) ((EntityDamageByEntityEvent) event).getDamager()).getShooter() instanceof LivingEntity){
                        damage((LivingEntity) event.getEntity(), (LivingEntity) ((Projectile) ((EntityDamageByEntityEvent) event).getDamager()).getShooter(), damage, EntityUtils.DamageType.PROJECTILE);
                        return;
                    }

                }
                //Melee by entity
                else {
                    //you never know, an item frame might just kill you
                    if (((EntityDamageByEntityEvent) event).getDamager() instanceof LivingEntity){
                        damage((LivingEntity) event.getEntity(), (LivingEntity) ((EntityDamageByEntityEvent) event).getDamager(), damage, EntityUtils.DamageType.MELEE);
                        return;
                    }

                }

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

            damage = ItemUtils.getEntityAttribute((LivingEntity) event.getDamager(), AttributeType.ATTACK_DAMAGE);


            //Direct Damage
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){


                //Cubing it makes spam much less effective
                damage *= Math.pow(((Player) event.getDamager()).getAttackCooldown(), 3);


                //Air strike
                if (sender.getFallDistance() > 1 && sender.getAttackCooldown() == 1 && EntityUtils.isPlayerWithEnch(CustomEnchantments.aerial_strike, sender, EquipmentSlot.HAND)){
                    damage *= 1 + Math.sqrt(sender.getFallDistance()) * EntityUtils.itemEnchLvl(CustomEnchantments.aerial_strike, sender.getInventory().getItemInMainHand()) / 10;
                }

                //If critical hit
                if (event.isCritical()){
                    damage *= 1.5;
                }


            }

            //Sweep damage
            else if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK){

                int sweepLvl = EntityUtils.itemEnchLvl(CustomEnchantments.sweeping_edge, sender.getInventory().getItemInMainHand());

                damage = 1 + (damage * sweepLvl / (sweepLvl + 1));
            }

        }

        //Ranged
        if(event.getDamager() instanceof Projectile){

            damage = ItemUtils.getEntityAttribute(sender, AttributeType.PROJECTILE_DAMAGE);


            //Damage depending on actual projectile speed compared to projectile speed stat
            if (event.getDamager() instanceof Arrow){
                damage *= EntityUtils.bowCharge(sender, (Projectile) event.getDamager());

            }

            //aoe damage for fireworks
            if (event.getDamager() instanceof Firework){

                damage *= Math.min(1, 1.5 - (event.getEntity().getLocation().distance(event.getDamager().getLocation()) / 5));

            }

            //Air strike for projectile weapons
            if (sender.getFallDistance() > 1 && EntityUtils.isPlayerWithEnch(CustomEnchantments.aerial_strike, sender, EquipmentSlot.HAND)){
                damage *= 1 + Math.sqrt(sender.getFallDistance()) * EntityUtils.itemEnchLvl(CustomEnchantments.aerial_strike, sender.getInventory().getItemInMainHand()) / 10;
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


    /**
     *
     * @param target target
     * @param damager damager
     * @param damage damage
     * @param damageType type of damage
     */
    public static void damage(LivingEntity target, LivingEntity damager, double damage, EntityUtils.DamageType damageType){

        damage(target, damager, damage, damageType, false, null);

    }


    /**
     *
     * @param target target
     * @param damager damager
     * @param damage damage
     * @param damageType type of damage
     * @param applyEffects if effects such as fire aspect and decay should be applied
     * @param directDamager the direct damage source, like arrows or other projectiles
     */
    public static void damage(LivingEntity target, LivingEntity damager, double damage, EntityUtils.DamageType damageType, boolean applyEffects, Entity directDamager){


        if (target.isDead() || (target instanceof Player && ((Player)target).getGameMode() == GameMode.CREATIVE) || target.isInvulnerable()){
            return;
        }



        damage = getDamage(target, damager, damage, damageType);

        target.playHurtAnimation(10);


        //Apply weapon effects
        if (applyEffects && damager instanceof Player){
            AttackEffectEnchantments.CheckAttackEffects(target, (Player) damager, directDamager);
        }

        //Apply damage

        //Go through absorption first
        if(target.getAbsorptionAmount() > 0){
            target.setAbsorptionAmount(Math.max(0, target.getAbsorptionAmount() - damage));
            damage = Math.max(0, damage - target.getAbsorptionAmount());
        }


        //If they should survive
        if (Math.floor(target.getHealth() - damage) > 0){

            target.setHealth(target.getHealth() - damage);

        }
        //If they should die
        else {

            //Disables Totems
            if (target.getEquipment().getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING){
                target.getEquipment().setItem(EquipmentSlot.HAND, new ItemStack(Material.AIR));
            }
            if (target.getEquipment().getItemInOffHand().getType() == Material.TOTEM_OF_UNDYING){
                target.getEquipment().setItem(EquipmentSlot.OFF_HAND, new ItemStack(Material.AIR));
            }


            target.setHealth(0);



            if(target instanceof Player){
                target.getPersistentDataContainer().set(Keys.DEAD, PersistentDataType.BOOLEAN, true);
                deathMessage((Player) target, damager, damageType);
            }

        }

    }

    private static void deathMessage(Player target, LivingEntity damager, EntityUtils.DamageType damageType){

        if (damager == null){

            String message;

            if (damageType == EntityUtils.DamageType.FALLING){
                message = " experienced deadly velocities";
            } else if (damageType == EntityUtils.DamageType.FIRE) {
                message = " burnt to a crisp";
            } else if (damageType == EntityUtils.DamageType.BLAST){
                message = " blew up";
            } else if (damageType == EntityUtils.DamageType.MAGIC){
                message = " was hexed to death by a mean wizard";
            } else {
                message = " was killed by unknown forces";
            }

            //Component component = Component.text(target.getName() + message);

            Bukkit.getServer().sendMessage(Component.text(target.getName() + message));

            return;
        }

        Bukkit.getServer().sendMessage(Component.text(target.getName() + " was killed by " + damager.getName()));
    }



    //removes death messages
    @EventHandler
    public void onDeath(PlayerDeathEvent event){

        event.deathMessage(Component.text(""));

    }





    //Damage after armor & protection & potion effect calculations
    public static double getDamage(LivingEntity target, LivingEntity damager, double damage, EntityUtils.DamageType damageType){



        int protLvl = 0;
        int secProtLvl = 0;



        //Attribute damage buffs
        if (damager instanceof Player){

            if (damageType == EntityUtils.DamageType.MELEE){

                damage += ItemUtils.getEntityAttribute(damager, AttributeType.ATTACK_DAMAGE, ItemUtils.AttributeOperator.ADD);
                damage *= ItemUtils.getEntityAttribute(damager, AttributeType.ATTACK_DAMAGE, ItemUtils.AttributeOperator.ADD_PROCENT);

            } else if (damageType == EntityUtils.DamageType.PROJECTILE) {
                damage += ItemUtils.getEntityAttribute(damager, AttributeType.PROJECTILE_DAMAGE, ItemUtils.AttributeOperator.ADD);
                damage *= ItemUtils.getEntityAttribute(damager, AttributeType.PROJECTILE_DAMAGE, ItemUtils.AttributeOperator.ADD_PROCENT);

            } else if (damageType == EntityUtils.DamageType.MAGIC){
                damage += ItemUtils.getEntityAttribute(damager, AttributeType.MAGIC_DAMAGE, ItemUtils.AttributeOperator.ADD);
                damage *= ItemUtils.getEntityAttribute(damager, AttributeType.MAGIC_DAMAGE, ItemUtils.AttributeOperator.ADD_PROCENT);

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

            //1.5 is for balancing
            damage *= Math.pow(0.96, 1.5 * ItemUtils.getEntityAttribute(target, AttributeType.ARMOR));

        }

        //Resistance
        int resEffect = 0;
        if (target.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
            resEffect = target.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier();
        }

        damage *= (1 - Math.min(1, resEffect * 0.2));



        return damage;


    }


}
