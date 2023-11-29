package pl.mn.mncustomenchants.EntityMethods.EntityEffects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;

public class StunEffect extends EntityEffect {


    LivingEntity entity;
    String stunName = "STUNNED_ATTR_NAME";
    AttributeModifier stunned;

    public StunEffect (LivingEntity entity){
        super(entity);
        this.entity = entity;

        stunned = new AttributeModifier(stunName, -1, AttributeModifier.Operation.MULTIPLY_SCALAR_1);


        if(EntityClassifications.containsAttribute(entity, Attribute.GENERIC_MOVEMENT_SPEED)){

            EntityClassifications.attachAttributeMod(entity, Attribute.GENERIC_MOVEMENT_SPEED, stunned);
        }

    }

    @Override
    public void run(){

        if(EntityClassifications.containsAttribute(entity, Attribute.GENERIC_MOVEMENT_SPEED)){

            EntityClassifications.detachAttributeMod(entity, Attribute.GENERIC_MOVEMENT_SPEED, stunName);

            EntityClassifications.activeEffects.remove(this);
        }

    }
}
