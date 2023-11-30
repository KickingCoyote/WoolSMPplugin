package pl.mn.mncustomenchants.EntityMethods.EntityEffects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class EntityEffect extends BukkitRunnable {

    public LivingEntity entity;


    public EntityEffect(LivingEntity entity){
        this.entity = entity;
    }

    public static void ApplyEffect (){

    }

}
