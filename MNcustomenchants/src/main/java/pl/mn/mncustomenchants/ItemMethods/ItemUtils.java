package pl.mn.mncustomenchants.ItemMethods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantment;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.Misc.Keys;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;
import java.util.Map;
import java.util.*;

import static java.lang.String.join;
import static java.lang.String.valueOf;
import static java.util.Collections.nCopies;


public class ItemUtils {




    public enum AttributeOperator {
        ITEM_STAT,
        ADD,
        ADD_PROCENT,
    };

    public static final List<String> tiers = List.of(
            "Common",
            "Rare",
            "Artifact",
            "Epic"
    );


    public static AttributeOperator AttributeOperatorValueOf(String s){
        AttributeOperator attributeOperator = null;

        for (AttributeOperator ao : AttributeOperator.values()){

            if (ao.name().equalsIgnoreCase(s)){
                return ao;
            }

        }

        return attributeOperator;
    }




    public static void setIdentifier(ItemStack itemStack, String identifier){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(Keys.IDENTIFIER, PersistentDataType.STRING, identifier);
        itemStack.setItemMeta(itemMeta);
    }

    public static String getIdentifier(ItemStack itemStack){
        if(itemStack.getItemMeta().getPersistentDataContainer().has(Keys.IDENTIFIER)){
            return itemStack.getItemMeta().getPersistentDataContainer().get(Keys.IDENTIFIER, PersistentDataType.STRING);
        }
        return null;
    }







    public static Attribute getAttributeFromKey(String keyAsString){

        String key;
        if (keyAsString.contains("mncustomenchants:")){
            key = keyAsString.split(":")[1].toUpperCase();
        } else {
            key = keyAsString.toUpperCase();
        }

        if (key.split("/").length < 3){
            return null;
        }

        AttributeOperator op = AttributeOperatorValueOf(key.split("/")[0]);
        EquipmentSlot eq = EquipmentSlot.valueOf(key.split("/")[1]);
        AttributeType at = AttributeType.valueOf(key.split("/")[2]);


        if (at == null || op == null){ return null; }

        return new Attribute(op, eq, at, 0);

    }


    //returns -2 000 000 if null;
    public static double getDataContainer(ItemStack itemStack, NamespacedKey key){
        if (!itemStack.hasItemMeta()) {return -2000000;}
        if (itemStack.getItemMeta().getPersistentDataContainer().isEmpty()) { return -2000000; }
        if (itemStack.getItemMeta().getPersistentDataContainer().has(key)) { return itemStack.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.DOUBLE); }

        return -2000000;
    }

    public static boolean hasDataContainer(ItemStack itemStack, NamespacedKey key){
        if (!itemStack.hasItemMeta()){return false;}

        return itemStack.getItemMeta().getPersistentDataContainer().has(key);
    }
    public static boolean hasDataContainer(ItemStack itemStack, NamespacedKey key, PersistentDataType dataType){
        if (!itemStack.hasItemMeta()){return false;}

        return itemStack.getItemMeta().getPersistentDataContainer().has(key, dataType);
    }



    public static double getEntityAttribute(LivingEntity target, AttributeType type){

        return (getItemStat(target, type) + getEntityAttribute(target, type, AttributeOperator.ADD)) * getEntityAttribute(target, type, AttributeOperator.ADD_PROCENT);

    }

    public static double getEntityAttribute(LivingEntity target, AttributeType type, AttributeOperator operator){

        double add = 0;
        double multiply = 0;

        if(target.getEquipment() == null){
            return 0;
        }

        for (EquipmentSlot eq : EquipmentSlot.values()){



            if (target.getEquipment().getItem(eq).isEmpty()) { continue; }
            if (getDataContainer(target.getEquipment().getItem(eq), Attribute.getKey(type, AttributeOperator.ADD, eq)) != -2000000)
            {
                add += getDataContainer(target.getEquipment().getItem(eq), Attribute.getKey(type, AttributeOperator.ADD, eq));
            }
            if (getDataContainer(target.getEquipment().getItem(eq), Attribute.getKey(type, AttributeOperator.ADD_PROCENT, eq)) != -2000000){
                multiply += getDataContainer(target.getEquipment().getItem(eq), Attribute.getKey(type, AttributeOperator.ADD_PROCENT, eq));
            }
        }

        if (operator == AttributeOperator.ITEM_STAT){
            return getItemStat(target, type);
        }
        if (operator == AttributeOperator.ADD){
            return add;
        }
        if (operator == AttributeOperator.ADD_PROCENT){
            return 1 + multiply;
        }

        return 0;
    }


    private static double getItemStat(LivingEntity target, AttributeType type){

        if(target.getEquipment() == null){
            return 0;
        }

        //TODO: prioritize so it chooses the highest value for armor and offhand
        //The order goes HAND, OFF_HAND, FEET -> HEAD
        for (EquipmentSlot e : EquipmentSlot.values()){



            ItemStack item = target.getEquipment().getItem(e);



            NamespacedKey key = Attribute.getKey(type, AttributeOperator.ITEM_STAT, EquipmentSlot.HAND);

            if (getDataContainer(item, key) != -2000000){
                return getDataContainer(item, key);
            }
        }


        //Values returned if the item has no matching Item Stat
        if (type == AttributeType.ATTACK_SPEED) { return 4; }
        if (type == AttributeType.PROJECTILE_SPEED) { return 1; }
        if (type == AttributeType.SPEED) { return 0.1; }
        if (type == AttributeType.HEALTH) { return 20; }

        return 0;
    }



    public static List<Attribute> GetItemAttributes(ItemStack itemStack){


        if (itemStack.getItemMeta().getPersistentDataContainer().isEmpty()){ return null; }

        List<Attribute> Attributes = new ArrayList<>();

        for (NamespacedKey key : itemStack.getItemMeta().getPersistentDataContainer().getKeys()){

            if (getAttributeFromKey(key.asString()) == null) {continue;}
            Attribute attr = getAttributeFromKey(key.asString());

            if (!itemStack.getItemMeta().getPersistentDataContainer().getKeys().contains(attr.getKey())) { continue; }


            attr.value = itemStack.getItemMeta().getPersistentDataContainer().get(attr.getKey(), PersistentDataType.DOUBLE);

            Attributes.add(attr);

        }

        return Attributes;
    }

    public static void RemoveAttribute(ItemStack itemStack, Attribute attribute){

        if (!itemStack.hasItemMeta()) { return; }
        if (itemStack.getItemMeta().getPersistentDataContainer().isEmpty()) { return; }
        if (!itemStack.getItemMeta().getPersistentDataContainer().getKeys().contains(attribute.getKey())) { return; }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().remove(attribute.getKey());

        itemStack.setItemMeta(itemMeta);
    }

    public static void AddAttribute (ItemStack itemStack, Attribute attribute, Double value){

        if (value == 0) {
            RemoveAttribute(itemStack, attribute);
            return;
        }


        ItemMeta itemMeta = itemStack.getItemMeta();


        itemMeta.getPersistentDataContainer().set(attribute.getKey(), PersistentDataType.DOUBLE, value);

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
    private static List<Attribute> GetAttributesOnSlot(List<Attribute> attributes, EquipmentSlot equipmentSlot){


        List<Attribute> attrOnSlot = new ArrayList<>();

        for (Attribute a : attributes){
            if (a.getSlot() == equipmentSlot) {
                attrOnSlot.add(a);
            }
        }

        return attrOnSlot;
    }


    public static void EditLore(ItemStack itemStack, int index, String component, int r, int g, int b){

        LoreComponent lore;
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(Keys.lore)){
            lore = itemStack.getItemMeta().getPersistentDataContainer().get(Keys.lore, new LoreComponentDataType());
        } else {
            lore = new LoreComponent(new String[10], new int[10], new int[10], new int[10]);
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        lore.appendText(component, index);
        lore.appendR(r, index);
        lore.appendG(g, index);
        lore.appendB(b, index);

        itemMeta.getPersistentDataContainer().set(Keys.lore, new LoreComponentDataType(), lore);

        itemStack.setItemMeta(itemMeta);

    }


    public static void UpdateLore(ItemStack itemStack){

        itemStack.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        List<Component> components = new ArrayList<>();

        //temporary Component
        Component component;

        //Custom Enchantments
        for (CustomEnchantment enchantment : CustomEnchantment.getAllItemEnchantments(itemStack)){
            int level = EntityUtils.itemEnchantmentLvl(enchantment, itemStack);

            if(level == 0) { continue; }

            component = enchantment.getName(level);

            component = component.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);

            components.add(component);
        }

        //Vanilla Enchantments
        for (Map.Entry<Enchantment, Integer> current : itemStack.getItemMeta().getEnchants().entrySet()) {

            if (current.getValue() == 0) {
                continue;
            }

            component = current.getKey().displayName(current.getValue());

            component = component.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);

            components.add(component);

        }



        //Tier
        String tier = getItemTier(itemStack);
        if (tier != null){

            TextColor color1;
            boolean bold = false;
            switch (tier) {
                case "Epic":
                    color1 = TextColor.color(170, 0, 170);
                    bold = true;
                    break;
                case "Rare":
                    color1 = TextColor.color(85, 255, 255);
                    break;
                case "Artifact":
                    color1 = TextColor.color(170, 0, 0);
                    break;
                default:
                    color1 = TextColor.color(170, 170, 170);
                    break;
            }

            Component component1 = Component.text(tier, color1).decoration(TextDecoration.BOLD, bold);
            components.add(Component.text("Overworld : ", TextColor.color(85, 85, 85)).append(component1).decoration(TextDecoration.ITALIC, false));


        }



        //The ACTUAL Lore

        if (itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(Keys.lore)){



            for (int i = 0;  i < itemStack.getItemMeta().getPersistentDataContainer().get(Keys.lore, new LoreComponentDataType()).getText().length; i++){
                LoreComponent s = itemStack.getItemMeta().getPersistentDataContainer().get(Keys.lore, new LoreComponentDataType());
                if (s.getText()[i] != null){
                    components.add(Component.text(s.getText()[i].replace('_', ' '), TextColor.color(s.getR()[i],s.getG()[i],s.getB()[i])).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
                }
            }


        }


        //Shattered
        if(hasDataContainer(itemStack, Keys.SHATTERED) && getShattered(itemStack) > 0){

            components.add(Component.text("* SHATTERED " + getRomanNumber(getShattered(itemStack))+ " *", TextColor.color(170, 0, 0)).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true));
        }




        //Attributes
        for (EquipmentSlot eq : EquipmentSlot.values()){

            if (ItemUtils.GetItemAttributes(itemStack) == null){ break; }


            List<Attribute> attrOnSlot = ItemUtils.GetAttributesOnSlot(ItemUtils.GetItemAttributes(itemStack), eq);

            attrOnSlot = sortAttributes(attrOnSlot);

            if (attrOnSlot.isEmpty()) { continue; }


            components.add(Component.text("").decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));

            String slotText;
            if (eq == EquipmentSlot.HAND) {
                slotText = "When in Main Hand: ";
            } else if (eq == EquipmentSlot.OFF_HAND){
                slotText = "When in Off Hand: ";
            } else {

                char[] s = eq.name().toLowerCase().toCharArray();
                s[0] -= 32;
                String s2 = "";
                for (char letter : s){
                    s2 += letter;
                }

                slotText = "When on " + s2 + ": ";
            }

            components.add(Component.text(slotText, TextColor.color(169,169,169)).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));


            for (Attribute a : attrOnSlot){


                String componentText = "";
                TextColor color = TextColor.color(85,85,255);
                Format format = new DecimalFormat("0.###");
                String operator = "+";
                if (a.value < 0)
                {
                    operator = "";
                    color = TextColor.color(255, 85, 85);
                }


                if(a.getOperator() == AttributeOperator.ADD)
                {
                    componentText = operator + format.format(a.value) + " " + a.getType().showName();
                }

                else if (a.getOperator() == AttributeOperator.ADD_PROCENT)
                {
                    componentText = operator + Math.round(a.value * 100) + "% " + a.getType().showName();
                }

                else if (a.getOperator() == AttributeOperator.ITEM_STAT)
                {
                    componentText = " " + format.format(a.value) + " " + a.getType().showName();
                    color = TextColor.color(0,169,0);
                }


                components.add(Component.text(componentText, color).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
            }


        }

        //Sets the lore
        ItemMeta iM = itemStack.getItemMeta();

        iM.lore(components);

        itemStack.setItemMeta(iM);

    }


    public static List<Attribute> sortAttributes (List<Attribute> list){


        Attribute[] arr = new Attribute[list.size()];

        for (int i = 0; i < list.size(); i++){
            arr[i] = list.get(i);
        }

        Arrays.sort(arr);

        List<Attribute> sortedList = new ArrayList<>();

        Collections.addAll(sortedList, arr);

        return sortedList;

    }

    public static void setItemTier(ItemStack itemStack, String tier){

        if (!tiers.contains(tier)){
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(Keys.TIER, PersistentDataType.STRING, tier);
        itemStack.setItemMeta(itemMeta);
    }

    public static String getItemTier(ItemStack itemStack){

        if (!itemStack.hasItemMeta()){return null;}
        if(!itemStack.getItemMeta().getPersistentDataContainer().has(Keys.TIER)) {return null;}

        return itemStack.getItemMeta().getPersistentDataContainer().get(Keys.TIER, PersistentDataType.STRING);
    }

    public static int getShattered(ItemStack item){
        if (!hasDataContainer(item, Keys.SHATTERED)){
            return 0;
        }

        return item.getItemMeta().getPersistentDataContainer().get(Keys.SHATTERED, PersistentDataType.INTEGER);
    }

    public static void setShattered(ItemStack item, int lvl){

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.getPersistentDataContainer().set(Keys.SHATTERED, PersistentDataType.INTEGER, lvl);

        item.setItemMeta(itemMeta);

    }

    /**
     * fixes items name being stored in a child object of the display name instead of directly in the display name
     */
    public static void fixBrokenName(ItemStack item){

        ItemMeta meta = item.getItemMeta();

        if (meta.displayName() == null){
            return;
        }

        if(meta.displayName().children().isEmpty()){
            return;
        }

        meta.displayName(meta.displayName().children().get(0));

        item.setItemMeta(meta);
    }

    

}
