package pl.mn.mncustomenchants.EntityMethods.EntityEffects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
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
            return;
        }

        if (entity.getHealth() < 1){
            entity.damage(2);
        }

        CustomDamage.damageEntity(entity, 1, EntityClassifications.DamageType.MAGIC);
        entity.playHurtAnimation(0.1f);
        for (Player player : entity.getLocation().getNearbyPlayers(10)){
            if (entity.getHurtSound() != null && frequency > 9){
                player.playSound(entity, entity.getHurtSound(), SoundCategory.HOSTILE, 1.5f, 1);
            }

        }

        count += frequency;


    }
}