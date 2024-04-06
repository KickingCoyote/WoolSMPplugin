package pl.mn.mncustomenchants.Spells.Spells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.Spells.SpellBases.LaserSpellBase;

import java.util.ArrayList;
import java.util.List;


public class SeekerLaserSpell extends LaserSpellBase {

    double damage;
    List<LivingEntity> excEntities = new ArrayList<>();
    ParticleData laserBaseParticles;


    //NOTE: wall-banging is possible if the target is pushed up against the corner
    //FIXME: swap out current detection system towards bounding boxes to fix this ^^^

    /**
     * laser that tracks the target then becomes stationary for a bit before firing an instant laser
     * @param pierce             if the laser should pierce entities
     * @param excEntities        entities ignored by the laser
     * @param damage             laser damage
     * @param caster             the entity casting the spell
     * @param castLocation       where the spell is cast
     * @param range              the lasers range
     * @param chargeTime         the time the laser follows the target before becoming stationary
     * @param delay              the time between becoming stationary and firing
     * @param laserBaseParticles the particle effect for laser, will always be a line
     */
    public SeekerLaserSpell(LivingEntity caster, Location castLocation, double range, int chargeTime, int delay, ParticleData laserBaseParticles, boolean pierce, double damage, List<LivingEntity> excEntities) {
        super(caster, castLocation, range, chargeTime, delay, pierce);

        this.damage = damage;
        this.excEntities.addAll(excEntities);
        this.laserBaseParticles = laserBaseParticles;
    }



    @Override
    public void onHit() {

        //Hit sounds:


        laserBaseParticles.color = Color.WHITE;
        laserBaseParticles.particleSize = 0.45f;
        Particles.RenderParticles(laserBaseParticles, castLocation.getWorld());


        Vector dir = endPoint.clone().subtract(castLocation).toVector().normalize();

        double length = castLocation.distance(actualLaserEnd);

        Location point = castLocation;


        for (double d = 0; d <= length; d += 0.1){

            for (Entity e : point.getNearbyEntities(0.2,0.2,0.2)){

                if (e instanceof LivingEntity && !excEntities.contains(e)){
                    CustomDamage.damage((LivingEntity) e, caster, damage, EntityUtils.DamageType.MAGIC);
                    excEntities.add((LivingEntity) e);

                    if(((LivingEntity)e).getHurtSound() != null && !e.isDead()){
                        castLocation.getWorld().playSound(e.getLocation(), ((LivingEntity)e).getHurtSound(), 1f, 1f);
                    }

                }
            }

            point = castLocation.clone().add(dir.clone().normalize().multiply(d));

        }




        onEnd();

    }

    @Override
    public void cancel() {

    }

    @Override
    protected void particles(double length) {

        laserBaseParticles.locations = Particles.line(castLocation, actualLaserEnd, length * 30);

        Particles.RenderParticles(laserBaseParticles, castLocation.getWorld());



        //Beam sounds:

    }
}
