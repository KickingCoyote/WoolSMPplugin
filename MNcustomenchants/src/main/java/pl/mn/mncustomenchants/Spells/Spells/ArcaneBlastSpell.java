package pl.mn.mncustomenchants.Spells.Spells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.Spells.SpellBases.RadialAoeSpellBase;

import java.util.List;

public class ArcaneBlastSpell extends RadialAoeSpellBase {

    double damage;
    ParticleData particleData;

    /**
     * @param caster       the entity casting the spell
     * @param castLocation where the spell is cast
     * @param radius       the aoe radius;
     * @param angle        the aoe angle, based around castLocation direction
     * @param height       the height of aoe area
     * @param damage       the damage (magic)
     * @param particleData particle effects
     */
    public ArcaneBlastSpell(LivingEntity caster, Location castLocation, double radius, double angle, double height, double damage, ParticleData particleData, List<LivingEntity> excludedEntities) {
        super(caster, castLocation, radius, angle, height, excludedEntities);

        this.damage = damage;
        this.particleData = particleData;
    }

    @Override
    public void onTick() {}

    @Override
    public void onHit() {

        particles();

        entities.removeAll(excludedEntities);
        for (LivingEntity entity : entities){

            CustomDamage.damage(entity, caster, damage, EntityUtils.DamageType.MAGIC);

        }

        onEnd();

    }

    private void particles(){


        Particles.RenderParticles(particleData, caster.getWorld());



    }

    @Override
    public void cancel() {}
}
