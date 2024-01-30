package pl.mn.mncustomenchants.ItemMethods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Debug;
import org.w3c.dom.Attr;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.CustomEnchantments.EnchatmentWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

import static java.lang.String.join;
import static java.util.Collections.nCopies;


public class ItemUtils {

    public enum AttributeOperator {
        ADD,
        MULTIPLY_PROCENT,
        ADD_PROCENT

    };




    public static final AttributeType THORNS = new AttributeType("THORNS",  "Thorns");
    public static final AttributeType ARMOR = new AttributeType("ARMOR",  "Armor");
    public static final AttributeType SPEED = new AttributeType("SPEED", "Speed");

    public static final List<AttributeType> AttributeTypes = List.of(
            THORNS,
            ARMOR,
            SPEED
    );

    public static AttributeType getAttributeTypeFromKey(String keyAsString){
        for (AttributeType at : AttributeTypes){

            String key;
            if (keyAsString.contains("mncustomenchants:")){
                key = keyAsString;
            } else {
                key = "mncustomenchants:" + keyAsString;
            }

            if (at.getKey().asString().equalsIgnoreCase(key)){
                return at;
            }
        }
        return null;
    }

    /*
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

     */


    public static Map<AttributeType, Attribute> GetItemAttributes(ItemStack itemStack){


        if (itemStack.getItemMeta().getPersistentDataContainer().isEmpty()){ return null; }

        Map<AttributeType, Attribute> Attributes = new HashMap<>();

        for (NamespacedKey key : itemStack.getItemMeta().getPersistentDataContainer().getKeys()){

            if (getAttributeTypeFromKey(key.asString()) == null) {continue;}

            Attributes.put(getAttributeTypeFromKey(key.asString()), itemStack.getItemMeta().getPersistentDataContainer().get(key, new AttributeDataType()));

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


        ItemMeta itemMeta = itemStack.getItemMeta();

        /*
        if (!itemMeta.getPersistentDataContainer().isEmpty() && itemMeta.getPersistentDataContainer().getKeys().contains(attributeType.getKey())){
            RemoveAttribute(itemStack, attributeType);
        }

         */

        itemMeta.getPersistentDataContainer().set(attributeType.getKey(), new AttributeDataType(), attribute);

        itemStack.setItemMeta(itemMeta);
    }

    public static String getRomanNumber(int number) {
        return join("", nCopies(number, "I"))
                .replace("IIIII", "V")
                .replace("IIII", "IV")
                .replace("VV", "X")
                .replace("VIV", "IX")
                .replace("XXXXX", "L")
                .replace("XXXX", "XL")
                .replace("LL", "C")
                .replace("LXL", "XC")
                .replace("CCCCC", "D")
                .replace("CCCC", "CD")
                .replace("DD", "M")
                .replace("DCD", "CM");
    }

    //Gets attributes with a certain slot
    private static Map<AttributeType, Attribute> GetAttributesOnSlot(Map<AttributeType, Attribute> attributes, EquipmentSlot equipmentSlot){

        Iterator<Map.Entry<AttributeType, Attribute>> attrIter = attributes.entrySet().iterator();

        Map<AttributeType, Attribute> attrOnSlot = new HashMap<>();

        while (attrIter.hasNext()){

            Map.Entry<AttributeType, Attribute> current = attrIter.next();

            if (current.getValue().getSlot() == equipmentSlot){
                attrOnSlot.put(current.getKey(), current.getValue());
            }

        }


        return attrOnSlot;
    }


    private static void AddLore(ItemStack itemStack, List<Component> components){
        ItemMeta iM = itemStack.getItemMeta();

        iM.lore(components);

        itemStack.setItemMeta(iM);

    }

    public static void UpdateLore(ItemStack itemStack){

        itemStack.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        List<Component> components = new ArrayList<>();

        //temporary Component
        Component component;

        Map<Enchantment, Integer> enchantments = itemStack.getItemMeta().getEnchants();
        Iterator<Map.Entry<Enchantment, Integer>> EnchIter = enchantments.entrySet().iterator();


        while (EnchIter.hasNext()){

            Map.Entry<Enchantment, Integer> current = EnchIter.next();

            if ( current.getValue() == 0) { continue; }

            String enchNamespace = current.getKey().getKey().asString().split(":")[1];

            if (CustomEnchantments.enchantmentArgs.contains(enchNamespace)){
                component = CustomEnchantments.valueOf(enchNamespace).displayName(current.getValue());
            } else {
                component = current.getKey().displayName(current.getValue());
            }

            component = component.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);

            components.add(component);

        }

        //The ACTUAL Lore



        //Attributes
        for (EquipmentSlot eq : EquipmentSlot.values()){

            if (ItemUtils.GetItemAttributes(itemStack) == null){ break; }


            Map<AttributeType, Attribute> attrOnSlot = ItemUtils.GetAttributesOnSlot(ItemUtils.GetItemAttributes(itemStack), eq);


            if (attrOnSlot.isEmpty()) { continue; }


            components.add(Component.text("").decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));

            String slotText;
            if (eq == EquipmentSlot.HAND) {
                slotText = "When in Main Hand: ";
            } else if (eq == EquipmentSlot.OFF_HAND){
                slotText = "When in Off Hand: ";
            } else {
                slotText = "When on " + eq.name() + ": ";
            }
            components.add(Component.text(slotText, TextColor.color(169,169,169)).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));


            Iterator<Map.Entry<AttributeType, Attribute>> attrIter = attrOnSlot.entrySet().iterator();
            while (attrIter.hasNext()){

                Map.Entry<AttributeType, Attribute> current = attrIter.next();

                components.add(Component.text(current.getValue().getValue() + " " + current.getKey().getShowName() + " " + current.getValue().getOperator(), TextColor.color(85, 85, 255)).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));

            }




        }



        //empty bar



        AddLore(itemStack, components);

    }



}
