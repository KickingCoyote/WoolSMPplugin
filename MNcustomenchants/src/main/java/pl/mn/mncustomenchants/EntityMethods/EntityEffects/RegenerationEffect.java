package pl.mn.mncustomenchants.EntityMethods.EntityEffects;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.checkerframework.checker.units.qual.A;

public class RegenerationEffect extends EntityEffect{

    LivingEntity entity;

    int level;

    public RegenerationEffect(LivingEntity entity, int level) {
        super(entity);

        this.level = level;
        this.entity = entity;
    }

    @Override
    public void run() {

        double regenAmount = Math.sqrt(level) / 12;

        if (entity.getHealth() + regenAmount <= entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() && entity.getHealth() >= 1){
            entity.setHealth(entity.getHealth() + regenAmount);
        }

    }
}
