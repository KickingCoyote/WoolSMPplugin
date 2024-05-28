package pl.mn.mncustomenchants.Spells.Spells;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Spells.SpellBases.HomingBoltSpellBase;

import java.util.List;

public class HomingBoltSpell extends HomingBoltSpellBase {

    double damage;

    /**
     * homing magic bolt, deals damage to any hit entities
     * @param damage           bolt damage
     * @param caster           the entity casting the spell
     * @param castLocation     where the spell is cast
     * @param target           the targeted entity
     * @param speed            the velocity of the bolt
     * @param particleData     the particles
     * @param maxDistance      the maximum distance the bolt can travel away from its cast location
     * @param size             the radius of the bolt
     * @param excludedEntities
     */

    public HomingBoltSpell(LivingEntity caster, Location castLocation, LivingEntity target, double speed, ParticleData particleData, double maxDistance, double size, List<LivingEntity> excludedEntities, double damage) {
        super(caster, castLocation, target, speed, particleData, maxDistance, size, excludedEntities);
        this.damage = damage;
    }

    @Override
    public void onHit() {
        for (LivingEntity e : targets){

            CustomDamage.damage(e, caster, damage, EntityUtils.DamageType.MAGIC);
            if(e.getHurtSound() != null){
                bolt.getWorld().playSound(bolt, e.getHurtSound(), 1f, 1f);
            }

        }
        onEnd();
    }

    @Override
    public void cancel() {

    }
}
