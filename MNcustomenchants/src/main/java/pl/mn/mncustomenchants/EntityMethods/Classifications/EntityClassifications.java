package pl.mn.mncustomenchants.EntityMethods.Classifications;

//import jdk.tools.jlink.internal.Archive;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;

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
    public static final List<EquipmentSlot> equipmentSlots = List.of(

            EquipmentSlot.HAND,
            EquipmentSlot.OFF_HAND,
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    );




    /*
    public static List<EntityEffect> activeEffects;

    public static List<EntityEffect> getActiveEffect(LivingEntity entity) {

        List<EntityEffect> eS = new ArrayList<EntityEffect>();


        for (EntityEffect e : activeEffects){
            if (e.entity == entity) {
                eS.add(e);
            }
        }

        return eS;

    } */

    public static int combinedEnchantLvl(Player player, Enchantment enchantment){

        int s = 0;

        for (EquipmentSlot e : equipmentSlots){
            if(isPlayerWithEnch(enchantment, player, e)){
                s += player.getInventory().getItem(e).getEnchantmentLevel(enchantment);
            }
        }


        return s;
    }
    public static int combinedAttributeLvl(LivingEntity entity, Attribute attribute){

        int s = 0;
        if (!containsAttribute(entity, attribute)){
            return 0;
        }
        if (entity.getAttribute(attribute) != null){
            s = (int)entity.getAttribute(attribute).getValue();
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

    public static boolean hasAttributeModifer(Attributable attributable, Attribute attribute, String modifierName){

        AttributeInstance instance = attributable.getAttribute(attribute);

        if(instance == null){return false;}
        for (AttributeModifier modifier : instance.getModifiers()){
            if(modifier.getName().equals(modifierName)){
                return true;
            }
        }

        return false;
    }

    public static void detachAttributeMod (Attributable attributable, Attribute attribute, String modifierName){
        AttributeInstance instance = attributable.getAttribute(attribute);

        if(instance == null){return;}
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
        if (!((Player) entity).getInventory().getItem(equipmentSlot).getItemMeta().hasEnchant(ench)) {
            return  false;
        }
        return ((Player) entity).getInventory().getItem(equipmentSlot).getItemMeta().getEnchantLevel(ench) != 0;
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
