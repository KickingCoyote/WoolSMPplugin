package pl.mn.mncustomenchants.Spells.SpellBases;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.MathUtils;
import pl.mn.mncustomenchants.Spells.Spell;

import java.util.ArrayList;
import java.util.List;

public abstract class RadialAoeSpellBase extends Spell {


    protected double radius;
    protected double angle;
    protected double height;
    protected List<LivingEntity> excludedEntities;

    protected List<LivingEntity> entities;

    /**
     * @param caster       the entity casting the spell
     * @param castLocation where the spell is cast
     * @param radius the aoe radius;
     * @param angle the aoe angle, based around castLocation direction
     * @param height the height of aoe area
     * @param excludedEntities entities not effected by aoe
     */
    public RadialAoeSpellBase(LivingEntity caster, Location castLocation, double radius, double angle, double height, List<LivingEntity> excludedEntities) {
        super(caster, castLocation);
        this.radius = radius;
        this.angle = angle;
        this.height = height;
        this.excludedEntities = excludedEntities;

        entities = new ArrayList<>();
    }


    @Override
    public void onCast() {

        for (Entity e : caster.getNearbyEntities(2 * radius, 2 * radius, 2 * radius)){

            if (!(e instanceof LivingEntity)){continue;}


            if (MathUtils.CyclicCollisionDetection(e.getLocation(), caster.getLocation(), radius, angle, height, 3)){
                entities.add((LivingEntity) e);
            }

        }

        entities.add(caster);

        entities.removeAll(excludedEntities);

        onHit();

    }



}
