package pl.mn.mncustomenchants.Spells.SpellBases;

import org.bukkit.Bukkit;
import  org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.Spells.Spell;

import java.util.ArrayList;
import java.util.List;

public abstract class HomingBoltSpellBase extends Spell {

    protected LivingEntity target;
    protected double speed;
    protected ParticleData particleData;
    protected double maxDistance;
    protected double size;

    protected Location bolt;
    protected List<LivingEntity> targets;
    protected List<LivingEntity> excludedEntities;
    protected Vector direction;

    /**
     * homing magic bolt
     * @param caster       the entity casting the spell
     * @param castLocation where the spell is cast
     * @param target       the targeted entity
     * @param speed        the velocity of the bolt
     * @param particleData the particles
     * @param maxDistance  the maximum distance the bolt can travel away from its cast location
     * @param size         the radius of the bolt
     */
    public HomingBoltSpellBase(LivingEntity caster, Location castLocation, LivingEntity target, double speed, ParticleData particleData, double maxDistance, double size, List<LivingEntity> excludedEntities) {
        super(caster, castLocation);

        this.target = target;
        this.speed = speed;
        this.particleData = particleData;
        this.maxDistance = maxDistance;
        this.size = size;
        this.excludedEntities = excludedEntities;

        bolt = castLocation.clone();

        targets = new ArrayList<>();
        direction = target.getEyeLocation().subtract(bolt).toVector().normalize();
    }

    @Override
    public void onTick() {

        //TODO: Homing does NOT work
        Location l = bolt.clone().add(direction.clone().multiply(2));
        Location l1 =  bolt.clone().add(target.getEyeLocation().subtract(bolt).toVector().normalize());

        if (l.getBlock().getType() != Material.AIR){
            target.sendMessage(l.getBlock().getLocation().toString());
        }
        if (bolt.getBlock().getRelative(0, 1, 0).getType() != Material.AIR){
            target.sendMessage(bolt.getBlock().getRelative(0, 1, 0).getLocation().toString() + "    :::::::");
        }


        if (l.getBlock().getType() != Material.AIR || l1.getBlock().getType() != Material.AIR){

            if (l.getBlock().getLocation() == bolt.getBlock().getRelative(0, 1, 0).getLocation()){
                direction = target.getEyeLocation().subtract(bolt).toVector().normalize().rotateAroundY(-90);

            }
            else {
                direction = target.getEyeLocation().subtract(bolt).toVector().normalize().rotateAroundZ(-90);
            }


        } else {
            direction = target.getEyeLocation().subtract(bolt).toVector().normalize();
        }

        bolt.add(direction.normalize().multiply(speed));

        particleData.locations = Particles.sphere(bolt, 100, size);


        Particles.RenderParticles(particleData, bolt.getWorld());


        if (bolt.distance(castLocation) > maxDistance){
            onEnd();
        }
        if(bolt.getWorld().getBlockAt(bolt).getType() != Material.AIR){
            onEnd();
        }


        for (Entity e : bolt.getNearbyEntities(size, size, size)){
            if (e instanceof LivingEntity){
                targets.add((LivingEntity) e);
            }
        }

        targets.removeAll(excludedEntities);

        if(!targets.isEmpty()){
            onHit();
        }

    }

    @Override
    public void onCast(){}


}
