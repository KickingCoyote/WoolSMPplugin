package pl.mn.mncustomenchants.Spells.Spells;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Spells.SpellBases.MagicBoltSpellBase;

import java.util.List;

public class MagicBoltSpell extends MagicBoltSpellBase {


    double damage;


    /**
     * Moving magic bolt, not homing, hurts entities
     *
     * @param caster           the entity casting the spell
     * @param castLocation     where the spell is cast
     * @param direction        direction of the bolt
     * @param speed            the velocity of the bolt
     * @param particleData     the particles
     * @param maxDistance      the maximum distance the bolt can travel
     * @param damage           bolt damage
     * @param excludedEntities entities ignored by the bolt
     */
    public MagicBoltSpell(LivingEntity caster, Location castLocation, Vector direction, double speed, ParticleData particleData, double maxDistance, double size, double damage, List<LivingEntity> excludedEntities) {
        super(caster, castLocation, direction, speed, particleData, maxDistance, size, excludedEntities);

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
