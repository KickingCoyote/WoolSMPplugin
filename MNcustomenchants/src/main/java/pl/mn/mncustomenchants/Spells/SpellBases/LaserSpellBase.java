package pl.mn.mncustomenchants.Spells.SpellBases;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Marker;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.MathUtils;
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

    protected List<Material> passThroughBlocks = List.of(
            Material.GLASS,
            Material.GLASS_PANE,
            Material.WHITE_STAINED_GLASS,
            Material.ORANGE_STAINED_GLASS,
            Material.MAGENTA_STAINED_GLASS,
            Material.LIGHT_BLUE_STAINED_GLASS,
            Material.YELLOW_STAINED_GLASS,
            Material.LIME_STAINED_GLASS,
            Material.PINK_STAINED_GLASS,
            Material.GRAY_STAINED_GLASS,
            Material.LIGHT_GRAY_STAINED_GLASS,
            Material.CYAN_STAINED_GLASS,
            Material.PURPLE_STAINED_GLASS,
            Material.BLUE_STAINED_GLASS,
            Material.BROWN_STAINED_GLASS,
            Material.GREEN_STAINED_GLASS,
            Material.RED_STAINED_GLASS,
            Material.BLACK_STAINED_GLASS,
            Material.WHITE_STAINED_GLASS_PANE,
            Material.ORANGE_STAINED_GLASS_PANE,
            Material.MAGENTA_STAINED_GLASS_PANE,
            Material.LIGHT_BLUE_STAINED_GLASS_PANE,
            Material.YELLOW_STAINED_GLASS_PANE,
            Material.LIME_STAINED_GLASS_PANE,
            Material.PINK_STAINED_GLASS_PANE,
            Material.GRAY_STAINED_GLASS_PANE,
            Material.LIGHT_GRAY_STAINED_GLASS_PANE,
            Material.CYAN_STAINED_GLASS_PANE,
            Material.PURPLE_STAINED_GLASS_PANE,
            Material.BLUE_STAINED_GLASS_PANE,
            Material.BROWN_STAINED_GLASS_PANE,
            Material.GREEN_STAINED_GLASS_PANE,
            Material.RED_STAINED_GLASS_PANE,
            Material.BLACK_STAINED_GLASS_PANE,
            Material.AIR
    );


    //NOTE: this class is a hot mess, don't question it, don't try to fix it, just accept that the magic spaghetti works
    //Its insane, Literally everything works, no bugs, no edge cases, no nothing, just *PERFECTION*

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



        //Calculate where the end point of the laser should be
        if(t <= chargeTime){


            Location rayBlock = caster.rayTraceBlocks(range, FluidCollisionMode.NEVER) == null ? null : caster.rayTraceBlocks(range, FluidCollisionMode.NEVER).getHitPosition().toLocation(castLocation.getWorld());
            Location rayEntity = pierce ? null : (caster.rayTraceEntities((int) range) == null ? null : caster.rayTraceEntities((int) range).getHitPosition().toLocation(castLocation.getWorld()));

            if (rayBlock != null && passThroughBlocks.contains(rayBlock.getBlock().getType())){
                rayBlock = null;

            }

            if(rayBlock != null && rayEntity != null){
                if (rayBlock.distance(castLocation) < rayEntity.distance(castLocation)){
                    endPoint = rayBlock;
                } else {
                    endPoint = rayEntity;
                }
            } else if (rayBlock != null){
                endPoint = rayBlock;
            } else if (rayEntity != null){
                endPoint = rayEntity;
            } else {
                //endPoint = castLocation.getDirection().normalize().multiply(range).add(castLocation.toVector()).toLocation(castLocation.getWorld());
                endPoint = castLocation.clone().add(castLocation.getDirection().normalize().multiply(range));
            }

        }

        actualLaserEnd = endPoint.clone();


        //Checks for any blocks in the way after chargeTime is over
        if (t > chargeTime) {

            castLocation.setDirection(endPoint.clone().subtract(castLocation).toVector());

            BlockIterator blockIterator = new BlockIterator(castLocation, 0, (int) range);

            double l = endPoint.distance(castLocation);

            //Entity collisions if no pierce
            if (!pierce){
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

            //Block collisions
            while (blockIterator.hasNext()){
                Block b = blockIterator.next();
                if(b.getBoundingBox().rayTrace(castLocation.toVector(), castLocation.getDirection(), l) != null && b.getLocation().distance(castLocation) < l && !passThroughBlocks.contains(b.getType())){

                    actualLaserEnd = castLocation.clone().add(castLocation.getDirection().clone().normalize().multiply(castLocation.distance(b.getLocation().add(0.5, 0.5, 0.5))));

                    break;
                }
            }



        }






        length = actualLaserEnd.distance(castLocation);




        if (t > delay + chargeTime){
            onHit();
            return;
        }

        particles(length);



        t += SpellManager.tickFrequency;
    }

    protected abstract void particles(double length);

    @Override
    public void onCast() {}

}
