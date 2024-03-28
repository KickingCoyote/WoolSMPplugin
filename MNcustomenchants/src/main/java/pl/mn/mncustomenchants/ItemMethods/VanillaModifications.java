package pl.mn.mncustomenchants.ItemMethods;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;

public class VanillaModifications {


    public static void Anvil (InventoryOpenEvent event){


        if (event.getInventory().getType() == InventoryType.ANVIL){
            if (!event.getPlayer().getInventory().getItemInMainHand().isEmpty()){
                if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() instanceof Damageable){
                    if (((Damageable) event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).getDamage() > 0){

                        ItemMeta itemMeta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                        ((Damageable)itemMeta).setDamage(0);

                        event.getPlayer().getInventory().getItemInMainHand().setItemMeta(itemMeta);

                        Block block = event.getInventory().getLocation().getBlock();

                        if (block.getType() == Material.ANVIL){
                            block.setType(Material.CHIPPED_ANVIL);
                        }else if (block.getType() == Material.CHIPPED_ANVIL){
                            block.setType(Material.DAMAGED_ANVIL);
                        }else if (block.getType() == Material.DAMAGED_ANVIL){
                            block.setType(Material.AIR);
                        }



                        event.getPlayer().getWorld().playSound(event.getPlayer(), Sound.BLOCK_ANVIL_PLACE,1, 1);
                    }
                }
            }
            event.setCancelled(true);

        }
    }

    public static void Enchanting (InventoryOpenEvent event){
        if (event.getInventory().getType() == InventoryType.ENCHANTING){
            event.setCancelled(true);
        }
    }

    public static void NonCustomItemsWithEnchants (Player player){


        for (EquipmentSlot e : EquipmentSlot.values()){
            if (!player.getInventory().getItem(e).isEmpty()){
                if (player.getInventory().getItem(e).hasItemMeta()){
                    if(!player.getInventory().getItem(e).getItemMeta().getPersistentDataContainer().has(Keys.custom_item)){

                        for (Enchantment enchantment : Enchantment.values()){
                            player.getInventory().getItem(e).removeEnchantment(enchantment);
                        }

                    } else if (!player.getInventory().getItem(e).getItemMeta().getPersistentDataContainer().get(Keys.custom_item, PersistentDataType.BOOLEAN).booleanValue()){

                        for (Enchantment enchantment : Enchantment.values()){
                            player.getInventory().getItem(e).removeEnchantment(enchantment);

                        }

                    }
                }
            }
        }

    }

    //The code for all the custom modifiers that use the vanilla modifier as a base
    public static void customToVanillaAttributes(Player player){


        //Gets current vanilla attack speed
        Double weaponAttackSpeed = 4.0;

        for (AttributeModifier a : player.getInventory().getItemInMainHand().getType().getDefaultAttributeModifiers(EquipmentSlot.HAND).get(Attribute.GENERIC_ATTACK_SPEED)){
            weaponAttackSpeed += a.getAmount();
        }


        //convert custom attributes to vanilla modifiers
        AttributeModifier attack_speed = new AttributeModifier("ATTACK_SPEED", ItemUtils.getPlayerAttribute(player, AttributeType.ATTACK_SPEED) - weaponAttackSpeed, AttributeModifier.Operation.ADD_NUMBER);
        AttributeModifier max_health = new AttributeModifier("MAX_HEALTH", ItemUtils.getPlayerAttribute(player, AttributeType.HEALTH) - 20, AttributeModifier.Operation.ADD_NUMBER);
        AttributeModifier movement_speed = new AttributeModifier("MOVEMENT_SPEED", ItemUtils.getPlayerAttribute(player, AttributeType.SPEED) -0.1, AttributeModifier.Operation.ADD_NUMBER);
        AttributeModifier knockback_resistance = new AttributeModifier("KNOCKBACK_RESISTANCE", ItemUtils.getPlayerAttribute(player, AttributeType.KNOCKBACK_RESISTANCE), AttributeModifier.Operation.ADD_NUMBER);

        //removes and reattaches the modifiers
        EntityUtils.detachAttributeMod(player, Attribute.GENERIC_MAX_HEALTH, "MAX_HEALTH");
        EntityUtils.detachAttributeMod(player, Attribute.GENERIC_ATTACK_SPEED, "ATTACK_SPEED");
        EntityUtils.detachAttributeMod(player, Attribute.GENERIC_MOVEMENT_SPEED, "MOVEMENT_SPEED");
        EntityUtils.detachAttributeMod(player, Attribute.GENERIC_KNOCKBACK_RESISTANCE, "KNOCKBACK_RESISTANCE");

        EntityUtils.attachAttributeMod(player, Attribute.GENERIC_ATTACK_SPEED, attack_speed);
        EntityUtils.attachAttributeMod(player, Attribute.GENERIC_MAX_HEALTH, max_health);
        EntityUtils.attachAttributeMod(player, Attribute.GENERIC_MOVEMENT_SPEED, movement_speed);
        EntityUtils.attachAttributeMod(player, Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockback_resistance);




    }


    //converts all vanilla gear into custom gear
    public static void vanillaToCustomAttributes(ItemStack itemStack){

        if (itemStack.hasItemMeta()){
            if (itemStack.getItemMeta().getPersistentDataContainer().getKeys().contains(Keys.custom_item)){
                return;
            }
        }

        if(itemStack.getType().getMaxDurability() == 0) { return; }


        //Clear enchants, fix flags, then add custom_item tag
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta.hasEnchants()){
            itemMeta.getEnchants().clear();
        }
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        itemMeta.getPersistentDataContainer().set(Keys.custom_item, PersistentDataType.BOOLEAN, true);

        itemStack.setItemMeta(itemMeta);



        //Convert Vanilla attributes to custom ones

        //Attack damage & speed
        double vAttackSpeed = 0;
        double vAttackDamage = 0;
        if (itemStack.getType().getDefaultAttributeModifiers(EquipmentSlot.HAND).containsKey(Attribute.GENERIC_ATTACK_DAMAGE)){
            vAttackDamage = itemStack.getType().getDefaultAttributeModifiers(EquipmentSlot.HAND).get(Attribute.GENERIC_ATTACK_DAMAGE).toArray(new AttributeModifier[1])[0].getAmount() + 1;
            vAttackSpeed = itemStack.getType().getDefaultAttributeModifiers(EquipmentSlot.HAND).get(Attribute.GENERIC_ATTACK_SPEED).toArray(new AttributeModifier[1])[0].getAmount() + 4;
        }

        ItemUtils.AddAttribute(itemStack, new pl.mn.mncustomenchants.ItemMethods.Attribute(ItemUtils.AttributeOperator.ITEM_STAT, EquipmentSlot.HAND, AttributeType.ATTACK_DAMAGE, 0), vAttackDamage);
        ItemUtils.AddAttribute(itemStack, new pl.mn.mncustomenchants.ItemMethods.Attribute(ItemUtils.AttributeOperator.ITEM_STAT, EquipmentSlot.HAND, AttributeType.ATTACK_SPEED, 0), vAttackSpeed);



        //Armor & Armor Toughness => Custom Armor
        for (EquipmentSlot eq : EquipmentSlot.values()){

            double vArmor = 0;
            double vKbResistance = 0;

            if (itemStack.getType().getDefaultAttributeModifiers(eq).containsKey(Attribute.GENERIC_ARMOR)){
                vArmor += itemStack.getType().getDefaultAttributeModifiers(eq).get(Attribute.GENERIC_ARMOR).toArray(new AttributeModifier[1])[0].getAmount();
            }
            if (itemStack.getType().getDefaultAttributeModifiers(eq).containsKey(Attribute.GENERIC_ARMOR_TOUGHNESS)){
                vArmor += itemStack.getType().getDefaultAttributeModifiers(eq).get(Attribute.GENERIC_ARMOR_TOUGHNESS).toArray(new AttributeModifier[1])[0].getAmount();
            }
            if (itemStack.getType().getDefaultAttributeModifiers(eq).containsKey(Attribute.GENERIC_KNOCKBACK_RESISTANCE)){
                vKbResistance = itemStack.getType().getDefaultAttributeModifiers(eq).get(Attribute.GENERIC_KNOCKBACK_RESISTANCE).toArray(new AttributeModifier[1])[0].getAmount();
            }


            ItemUtils.AddAttribute(itemStack, new pl.mn.mncustomenchants.ItemMethods.Attribute(ItemUtils.AttributeOperator.ADD, eq, AttributeType.ARMOR, 0), vArmor);
            ItemUtils.AddAttribute(itemStack, new pl.mn.mncustomenchants.ItemMethods.Attribute(ItemUtils.AttributeOperator.ADD, eq, AttributeType.KNOCKBACK_RESISTANCE, 0), vKbResistance);

        }


        //Add custom attributes to bows and crossbows
        if (itemStack.getType() == Material.BOW){
            ItemUtils.AddAttribute(itemStack, new pl.mn.mncustomenchants.ItemMethods.Attribute(ItemUtils.AttributeOperator.ITEM_STAT, EquipmentSlot.HAND, AttributeType.PROJECTILE_DAMAGE, 0), 6.0);
            ItemUtils.AddAttribute(itemStack, new pl.mn.mncustomenchants.ItemMethods.Attribute(ItemUtils.AttributeOperator.ITEM_STAT, EquipmentSlot.HAND, AttributeType.PROJECTILE_SPEED, 0), 1.0);
        }
        if (itemStack.getType() == Material.CROSSBOW){
            ItemUtils.AddAttribute(itemStack, new pl.mn.mncustomenchants.ItemMethods.Attribute(ItemUtils.AttributeOperator.ITEM_STAT, EquipmentSlot.HAND, AttributeType.PROJECTILE_DAMAGE, 0), 8.0);
            ItemUtils.AddAttribute(itemStack, new pl.mn.mncustomenchants.ItemMethods.Attribute(ItemUtils.AttributeOperator.ITEM_STAT, EquipmentSlot.HAND, AttributeType.PROJECTILE_SPEED, 0), 1.0);
        }



        ItemUtils.UpdateLore(itemStack);



    }



}
