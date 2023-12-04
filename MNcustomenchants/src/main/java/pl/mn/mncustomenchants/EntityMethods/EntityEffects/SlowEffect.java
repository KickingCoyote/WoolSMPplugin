package pl.mn.mncustomenchants.EntityMethods.EntityEffects;

import org.bukkit.entity.LivingEntity;

public class SlowEffect extends EntityEffect{

    LivingEntity entity;

    public SlowEffect(LivingEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public void run() {

    }
}
