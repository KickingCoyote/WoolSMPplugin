package pl.mn.mncustomenchants.ItemMethods;

import org.bukkit.inventory.EquipmentSlot;

import java.io.Serializable;

public class Attribute implements Serializable {



    private ItemUtils.AttributeOperator operator;

    private EquipmentSlot slot;

    private double value;

    public Attribute(ItemUtils.AttributeOperator operator, EquipmentSlot slot, Double value){
        this.operator = operator;
        this.slot = slot;
        this.value = value;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
