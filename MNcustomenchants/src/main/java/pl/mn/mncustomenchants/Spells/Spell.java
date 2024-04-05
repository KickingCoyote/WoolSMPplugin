package pl.mn.mncustomenchants.Spells;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;

public abstract class Spell {


    public LivingEntity caster;
    public Location castLocation;

    /**
     *
     * @param caster the entity casting the spell
     * @param castLocation where the spell is cast
     */
    public Spell(LivingEntity caster, Location castLocation){
        this.caster = caster;
        this.castLocation = castLocation;
    }

    public abstract void onTick();

    public abstract void onCast();

    public abstract void onHit();

    public abstract void cancel();

    public void onEnd(){
        SpellManager.removeSpell(this);
    }



}
