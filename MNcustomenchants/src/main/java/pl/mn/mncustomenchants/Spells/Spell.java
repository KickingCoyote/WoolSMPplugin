package pl.mn.mncustomenchants.Spells;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;

public abstract class Spell {


    public LivingEntity caster;
    public Location castLocation;



    public abstract void onTick();

    public abstract void onCast();

    public abstract void onHit();

    public abstract void cancel();

    public abstract void onEnd();



}
