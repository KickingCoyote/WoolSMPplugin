package pl.mn.mncustomenchants.EntityMethods.EntityEffects.PassiveEffects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;

public class PassiveEffects {

    public static void Curse (Player player, boolean bool){

        String speedName = "jdsfisjf";
        String AttackSpeedName = "jdsadasdasd";

        AttributeModifier curseSpeed = new AttributeModifier(speedName, -1, AttributeModifier.Operation.MULTIPLY_SCALAR_1);
        AttributeModifier curseAttackSpeed = new AttributeModifier(AttackSpeedName, -4, AttributeModifier.Operation.ADD_NUMBER);

        if(bool){
            EntityUtils.attachAttributeMod(player, Attribute.GENERIC_MOVEMENT_SPEED, curseSpeed);
            EntityUtils.attachAttributeMod(player, Attribute.GENERIC_ATTACK_SPEED, curseAttackSpeed);
        }else {
            EntityUtils.detachAttributeMod(player, Attribute.GENERIC_ATTACK_SPEED, AttackSpeedName);
            EntityUtils.detachAttributeMod(player, Attribute.GENERIC_MOVEMENT_SPEED, speedName);
        }

    }

}
