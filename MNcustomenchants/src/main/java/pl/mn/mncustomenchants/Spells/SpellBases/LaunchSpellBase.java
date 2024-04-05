package pl.mn.mncustomenchants.Spells.SpellBases;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.Spells.Spell;

public abstract class LaunchSpellBase extends Spell {


    protected Entity entity;
    protected Vector direction;
    protected double speed;


    /**
     *
     * @param caster caster
     * @param castLocation cast location
     * @param entity the launched entity
     * @param direction the launch direction
     * @param speed launch velocity
     */
    public LaunchSpellBase(LivingEntity caster, Location castLocation, Entity entity, Vector direction, double speed){
        super(caster, castLocation);
        this.entity = entity;
        this.direction = direction;
        this.speed = speed;
    }


    @Override
    public void onCast() {
        entity.setVelocity(direction.normalize().multiply(speed));
    }


}
