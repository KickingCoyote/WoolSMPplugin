package pl.mn.mncustomenchants.ItemMethods;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import pl.mn.mncustomenchants.main;

import java.io.Serializable;

public class Attribute implements Serializable, Comparable<Attribute> {



    private ItemUtils.AttributeOperator operator;

    private EquipmentSlot slot;

    private AttributeType type;

    private NamespacedKey key;

    public double value;

    public Attribute(ItemUtils.AttributeOperator operator, EquipmentSlot slot, AttributeType type, double value){
        this.operator = operator;
        this.slot = slot;
        this.type = type;
        this.value = value;
        key = Attribute.getKey(type, operator, slot);
    }

    @Override
    public int compareTo(Attribute o) {
        if (operator.compareTo(o.operator) == 0){
            return type.compareTo(o.type);
        }
        return operator.compareTo(o.operator);
    }


    public ItemUtils.AttributeOperator getOperator() {
        return operator;
    }

    public void setOperator(ItemUtils.AttributeOperator operator) {
        this.operator = operator;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }

    public void setSlot(EquipmentSlot slot) {
        this.slot = slot;
    }


    public AttributeType getType() {
        return type;
    }


    public NamespacedKey getKey() {
        return key;
    }

    public static NamespacedKey getKey(AttributeType type, ItemUtils.AttributeOperator operator, EquipmentSlot slot){
        return new NamespacedKey(main.getInstance(), operator + "X" + slot + "X" + type.getName());
    }


}
