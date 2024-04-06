package pl.mn.mncustomenchants.Spells.SpellBases;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Marker;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.MathUtils;
import pl.mn.mncustomenchants.Misc.C;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.Spells.Spell;
import pl.mn.mncustomenchants.Spells.SpellManager;

import java.util.List;
import java.util.function.Consumer;

public abstract class LaserSpellBase extends Spell {

    protected int chargeTime;
    protected int delay;
    protected double range;
    protected boolean pierce;

    protected int t;
    protected Location endPoint;
    protected double length;

    //This is the point where the laser actually end everything else is just intermediate steps
    protected Location actualLaserEnd;




    //NOTE: this class is a hot mess, don't question it, don't try to fix it, just accept that the magic spaghetti works
    //Its insane, Literally everything works, no bugs, no edge cases, no nothing, just *PERFECTION*
    //NOTE 2: the laser is fired from your eyes but the particles are fired from a little below to make the less annoying, this is intentional but can look a bit weird
    //NOTE 3: particles don't render in colored glass / water, but that's a minecraft issue

    /**
     * laser that tracks the target then becomes stationary for a bit before instantly firing
     * @param pierce         if the laser should pierce entities
     * @param delay          the time between becoming stationary and firing
     * @param chargeTime     the time the laser follows the target before becoming stationary
     * @param range          the lasers range
     * @param caster         the entity casting the spell
     * @param castLocation   where the spell is cast
     */
    public LaserSpellBase(LivingEntity caster, Location castLocation, double range, int chargeTime, int delay, boolean pierce) {
        super(caster, castLocation);

        this.range = range;
        this.chargeTime = chargeTime;
        this.delay = delay;
        this.pierce = pierce;

        t = 0;
    }

    @Override
    public void onTick() {


        //lower by 0.15 to hinder the particles from being in the middle of the screen when the caster is a player
        castLocation = caster.getEyeLocation().subtract(0, 0.15, 0);



        //Calculate where the end point of the laser should be whilst still charging
        if(t <= chargeTime){
            endPoint = castLocation.clone().add(castLocation.getDirection().normalize().multiply(range));
        }


        actualLaserEnd = endPoint.clone();
        castLocation.setDirection(endPoint.clone().subtract(castLocation).toVector());



        //Entity collisions if no pierce
        if (!pierce){
            EntityCollisions();
        }


        //Check for block collisions
        BlockCollisions();


        if (t > delay + chargeTime){
            onHit();
            return;
        }

        length = actualLaserEnd.distance(castLocation);
        particles(length);
        t += SpellManager.tickFrequency;
    }

    protected abstract void particles(double length);


    @Override
    public void onCast() {}



    protected void BlockCollisions(){
        double l = endPoint.distance(castLocation);
        BlockIterator blockIterator = new BlockIterator(castLocation, 0, (int) range);
        boolean foundBlock = false;

        //Block collisions
        while (blockIterator.hasNext()){

            if (foundBlock){break;}

            Block b = blockIterator.next();

            for (BoundingBox bb : b.getCollisionShape().getBoundingBoxes()){

                bb.shift(b.getLocation());


                if(b.getBoundingBox().copy(bb).rayTrace(castLocation.toVector(), castLocation.getDirection(), l) != null && b.getLocation().distance(castLocation) < l && !C.lightPassThroughBlocks.contains(b.getType())){

                    actualLaserEnd = castLocation.clone().add(castLocation.getDirection().clone().normalize().multiply(castLocation.distance(b.getLocation().add(0.5, 0.5, 0.5))));

                    foundBlock = true;
                    break;
                }
            }
        }
    }

    protected void EntityCollisions(){

        double l = endPoint.distance(castLocation);

        for (Entity entity : castLocation.toVector().getMidpoint(endPoint.toVector()).toLocation(castLocation.getWorld()).getNearbyEntities(l, l, l)){

            if (entity == caster){
                continue;
            }

            RayTraceResult r = entity.getBoundingBox().rayTrace(castLocation.toVector(), castLocation.getDirection(), l);

            if (r != null && r.getHitPosition().toLocation(castLocation.getWorld()).distance(castLocation) < l){

                actualLaserEnd = castLocation.clone().add(castLocation.getDirection().clone().normalize().multiply(castLocation.distance(entity.getLocation().add(0.5, 0.5, 0.5))));

            }

        }
    }


}
