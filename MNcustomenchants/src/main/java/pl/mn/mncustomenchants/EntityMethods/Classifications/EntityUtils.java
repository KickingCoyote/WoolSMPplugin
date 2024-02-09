package pl.mn.mncustomenchants.EntityMethods.Classifications;

//import jdk.tools.jlink.internal.Archive;
import org.bukkit.*;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.ItemMethods.AttributeType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;

import java.util.*;

public class EntityUtils {
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


    public static double bowCharge(Player sender, Projectile projectile){

        if (sender.getInventory().getItemInMainHand().getType() != Material.BOW) { return 1; }


        double projSpeed = ItemUtils.getPlayerAttribute(sender, AttributeType.PROJECTILE_SPEED);

        //A value between 0 and 1 based on the arrow velocity that determines the damage
        double bowCharge =  Math.min(3 * projSpeed, projectile.getVelocity().length()) / (3 * projSpeed);
        //if bowCharge is above 0.9 round it to 1 for nicer numbers
        if (bowCharge > 0.9) {
            bowCharge = 1;
        }

        return bowCharge;
    }



    public static int combinedEnchantLvl(Player player, Enchantment enchantment){

        int s = 0;

        for (EquipmentSlot e : EquipmentSlot.values()){
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
        return  itemEnchLvl(ench, ((Player) entity).getInventory().getItem(equipmentSlot)) != 0;
    }

    //Returns enchantment lvl on itemstack
    //returns 0 if the item lacks the enchantment
    public static int itemEnchLvl(Enchantment ench, ItemStack itemStack){

        if (!itemStack.hasItemMeta()) {
            return 0;
        }

        if (!itemStack.getItemMeta().hasEnchant(ench)) {
            return  0;
        }

        return itemStack.getItemMeta().getEnchantLevel(ench);

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



    public static void playSound(Location location, int radius, Sound sound, SoundCategory category, float volume, float pitch){
        for (Player player : location.getWorld().getPlayers()){
            if (location.distance(player.getLocation()) < radius){
                player.playSound(location, sound, category, volume, pitch);
            }
        }
    }




}
