package pl.mn.mncustomenchants.EntityMethods.EntityEffects;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.Decay;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;

public class DecayEffect extends EntityEffect {

    LivingEntity entity;

    int frequency;
    int count;



    public DecayEffect (LivingEntity entity, int frequency){
        super(entity);

        this.entity = entity;
        this.frequency = frequency;
        count = 0;
    }

    @Override
    public void run() {

        if(count > 80 || entity.isDead() || this.isCancelled()){
            this.cancel();
            EntityClassifications.activeEffects.remove(this);
        }

        if (entity.getHealth() < 1){
            entity.damage(2);
        }

        EntityClassifications.damageEntity(entity, 1, EntityClassifications.DamageType.MAGIC);


        count += frequency;


    }
}
