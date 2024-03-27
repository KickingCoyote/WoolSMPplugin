package pl.mn.mncustomenchants.Spells.Spells;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.Spells.Spell;
import pl.mn.mncustomenchants.Spells.SpellManager;


public class VolleySpell extends Spell {


    double angle;
    float speed;
    int count;
    Vector direction;

    /**
     *
     * @param caster caster
     * @param castLocation cast location
     * @param direction the cast direction
     * @param angle the cone angle in radians, 2 pi is a full circle
     * @param speed arrow velocity
     * @param count amount of arrows
     */
    public VolleySpell(LivingEntity caster, Location castLocation, Vector direction, double angle, float speed, int count){

        this.caster = caster;
        this.castLocation = castLocation;

        this.direction = direction;
        this.angle = angle;
        this.speed = speed;
        this.count = count;
    }


    @Override
    public void onTick() {

    }

    @Override
    public void onCast() {

        Vector v = direction;

        for (double i = 0; i < count; i++){

            v.rotateAroundY(angle / count);


            Arrow a = caster.getWorld().spawnArrow(castLocation, v, speed, 1);
            a.setShooter(caster);
            a.setLifetimeTicks(1170);


        }

    }

    @Override
    public void onHit() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void onEnd() {
        SpellManager.removeSpell(this);
    }
}
