package pl.mn.mncustomenchants.Spells.SpellBases;

import io.papermc.paper.plugin.loader.library.LibraryLoadingException;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;
import org.yaml.snakeyaml.error.Mark;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.Spells.Spell;

import java.util.ArrayList;
import java.util.List;

public abstract class MagicBoltSpellBase extends Spell {

    protected Vector direction;
    protected double speed;
    protected ParticleData particleData;
    protected double maxDistance;
    protected double size;

    protected Location bolt;
    protected List<LivingEntity> targets;
    protected List<LivingEntity> excludedEntities;


    /**
     * Moving magic bolt, not homing
     * @param caster       the entity casting the spell
     * @param castLocation where the spell is cast
     * @param direction    direction of the bolt
     * @param speed        the velocity of the bolt
     * @param particleData the particles
     * @param maxDistance  the maximum distance the bolt can travel
     * @param size         the radius of the bolt
     */
    public MagicBoltSpellBase(LivingEntity caster, Location castLocation, Vector direction, double speed, ParticleData particleData, double maxDistance, double size, List<LivingEntity> excludedEntities) {
        super(caster, castLocation);

        this.direction = direction;
        this.speed = speed;
        this.particleData = particleData;
        this.maxDistance = maxDistance;
        this.size = size;
        this.excludedEntities = excludedEntities;

        bolt = castLocation.clone();

        targets = new ArrayList<>();
    }

    @Override
    public void onTick() {

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
