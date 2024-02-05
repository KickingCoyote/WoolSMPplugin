package pl.mn.mncustomenchants.ItemMethods;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;

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


        //removes and reattaches the modifiers
        EntityClassifications.detachAttributeMod(player, Attribute.GENERIC_MAX_HEALTH, "MAX_HEALTH");
        EntityClassifications.detachAttributeMod(player, Attribute.GENERIC_ATTACK_SPEED, "ATTACK_SPEED");
        EntityClassifications.detachAttributeMod(player, Attribute.GENERIC_MOVEMENT_SPEED, "MOVEMENT_SPEED");
        EntityClassifications.attachAttributeMod(player, Attribute.GENERIC_ATTACK_SPEED, attack_speed);
        EntityClassifications.attachAttributeMod(player, Attribute.GENERIC_MAX_HEALTH, max_health);
        EntityClassifications.attachAttributeMod(player, Attribute.GENERIC_MOVEMENT_SPEED, movement_speed);




    }


    //converts all vanilla gear into custom gear
    public static void vanillaToCustomAttributes(){


    }



}
