package pl.mn.mncustomenchants.ItemMethods;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;


public class ItemUtils {

    public enum AttributeOperator {
        ADD,
        MULTIPLY_PROCENT,
        ADD_PROCENT

    };




    public static final AttributeType THORNS = new AttributeType("THORNS",  "Thorns");

    public static final List<AttributeType> AttributeTypes = List.of(
      THORNS

    );

    public static AttributeType getAttributeTypeFromKey(NamespacedKey key){
        for (AttributeType at : AttributeTypes){
            if (at.getKey() == key){
                return at;
            }
        }
        return null;
    }

    public static double GetPlayerAttribute(AttributeType attributeType, Player player){

        double totalLvl = 0;

        for (EquipmentSlot eq : EquipmentSlot.values()){

            if (player.getInventory().getItem(eq).isEmpty()){ continue; }
            Attribute attribute = GetItemAttribute(attributeType, player.getInventory().getItem(eq));

            if (attribute == null) { continue; }
            if (attribute.getSlot() == eq){
                totalLvl += attribute.getValue();
            }

        }
        return totalLvl;
    }

    public static Attribute GetItemAttribute(AttributeType attributeType, ItemStack itemStack){

        if (!itemStack.hasItemMeta()) { return null; }
        if (itemStack.getItemMeta().getPersistentDataContainer().isEmpty()) { return null; }

        return itemStack.getItemMeta().getPersistentDataContainer().get(attributeType.getKey(), new AttributeDataType());

    }


    public static Map<AttributeType, Attribute> GetItemAttributes(ItemStack itemStack){


        if (!itemStack.hasItemMeta()){ return null; }

        if (itemStack.getItemMeta().getPersistentDataContainer().isEmpty()){ return null; }

        Map<AttributeType, Attribute> Attributes = new HashMap<>();

        for (NamespacedKey key : itemStack.getItemMeta().getPersistentDataContainer().getKeys()){
            if (getAttributeTypeFromKey(key) == null) {continue;}

            Attributes.put(getAttributeTypeFromKey(key), itemStack.getItemMeta().getPersistentDataContainer().get(key, new AttributeDataType()));

        }

        return Attributes;
    }

    public static void RemoveAttribute(ItemStack itemStack, AttributeType attributeType){

        if (!itemStack.hasItemMeta()) { return; }
        if (itemStack.getItemMeta().getPersistentDataContainer().isEmpty()) { return; }
        if (!itemStack.getItemMeta().getPersistentDataContainer().getKeys().contains(attributeType.getKey())) { return; }

        itemStack.getItemMeta().getPersistentDataContainer().remove(attributeType.getKey());

    }

    public static void AddAttribute (ItemStack itemStack, AttributeType attributeType, Attribute attribute){
        if (!itemStack.hasItemMeta()) { return; }
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (!itemMeta.getPersistentDataContainer().isEmpty() && itemMeta.getPersistentDataContainer().getKeys().contains(attributeType.getKey())){
            RemoveAttribute(itemStack, attributeType);
        }

        itemMeta.getPersistentDataContainer().set(attributeType.getKey(), new AttributeDataType(), attribute);


    }



}
