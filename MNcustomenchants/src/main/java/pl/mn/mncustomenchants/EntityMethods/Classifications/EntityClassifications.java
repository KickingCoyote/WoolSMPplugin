package pl.mn.mncustomenchants.EntityMethods.Classifications;

//import jdk.tools.jlink.internal.Archive;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.DecayEffect;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.EntityEffect;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class EntityClassifications {
    public static final EnumSet<EntityType> FlyingEntities = EnumSet.of(
            EntityType.BEE,
            EntityType.VEX,
            EntityType.ALLAY,
            EntityType.BAT,
            EntityType.WITHER,
            EntityType.BLAZE,
            EntityType.PARROT,
            EntityType.PHANTOM,
            EntityType.GHAST
    );
    public enum DamageType{
        MELEE,
        PROJECTILE,
        MAGIC,
        FALLING,
        FIRE,
        BLAST,
        TRUE
    }





    public static List<EntityEffect> activeEffects;

    public static List<EntityEffect> getActiveEffect(LivingEntity entity) {

        List<EntityEffect> eS = new ArrayList<EntityEffect>();


        for (EntityEffect e : activeEffects){
            if (e.entity == entity) {
                eS.add(e);
            }
        }

        return eS;

    }

    public static int combinedEnchantLvl(Player player, Enchantment enchantment){

        final List<EquipmentSlot> eqSs = List.of(

                EquipmentSlot.HAND,
                EquipmentSlot.OFF_HAND,
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        );

        int s = 0;

        for (EquipmentSlot e : eqSs){
            if(isPlayerWithEnch(enchantment, player, e)){
                s += player.getInventory().getItem(e).getEnchantmentLevel(enchantment);
            }
        }


        return s;
    }


    public static boolean containsAttribute(LivingEntity entity, Attribute attribute){

        try {
            entity.getAttribute(attribute).getBaseValue();
        } catch (Exception e){
            return false;
        }

        return true;
    }
    public  static void  attachAttributeMod (Attributable attributable, Attribute attribute, AttributeModifier modifier){
        AttributeInstance instance = attributable.getAttribute(attribute);
        instance.addTransientModifier(modifier);

    }

    public static void detachAttributeMod (Attributable attributable, Attribute attribute, String modifierName){
        AttributeInstance instance = attributable.getAttribute(attribute);

        for (AttributeModifier modifier : instance.getModifiers()){
            if(modifier.getName().equals(modifierName)){
                instance.removeModifier(modifier);
            }
        }

    }
    public static boolean isPlayerWithEnch(Enchantment ench, Entity entity, EquipmentSlot equipmentSlot){
        if(!(entity instanceof Player))
            return false;
        if (((Player) entity).getInventory().getItem(equipmentSlot).equals(ItemStack.empty()))
            return false;
        if (!((Player) entity).getInventory().getItem(equipmentSlot).hasItemMeta())
            return false;
        return ((Player) entity).getInventory().getItem(equipmentSlot).getItemMeta().hasEnchant(ench);
    }

    public static void damageEntity(LivingEntity entity, double damage, DamageType damageType){

        //IGNORES IFRAMES

        double finalDamage = 0;
        if(damageType == DamageType.TRUE){
            finalDamage = damage;
        } else if (damageType == DamageType.MAGIC){

            if(entity instanceof Player){
                double lvl = combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_ENVIRONMENTAL);
                finalDamage = damage * (1 - Math.min(0.8, lvl * 0.04));
            }
            else {
                finalDamage = damage;
            }
        } else if (damageType == DamageType.PROJECTILE) {
            if(entity instanceof Player){
                double protLvl = combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_ENVIRONMENTAL);
                double projLvl = combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_PROJECTILE);
                finalDamage = damage * (1 - Math.min(0.8, (protLvl * 0.04) + (projLvl * 0.08)));
            }
            else {
                finalDamage = damage;
            }
        } else if (damageType == DamageType.FIRE){
            if(entity instanceof Player){
                double protLvl = combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_ENVIRONMENTAL);
                double fireLvl = combinedEnchantLvl((Player) entity, Enchantment.PROTECTION_FIRE);
                finalDamage = damage * (1 - Math.min(0.8, (protLvl * 0.04) + (fireLvl * 0.08)));
            }
            else {
                finalDamage = damage;
            }
        }




        entity.setHealth(entity.getHealth() - finalDamage);

        if(entity.getHealth() < 0.5){
            entity.damage(1);
        }

    }

    public static double getDistance (Entity entity1, Entity entity2){

        Vector vector1 = entity1.getLocation().toVector();
        Vector vector2 = entity2.getLocation().toVector();

        return vector1.distance(vector2);

    }

    public static boolean getChance(int n, int r){
        Random random = new Random();
        return random.nextInt(n) < r;
    }




}
