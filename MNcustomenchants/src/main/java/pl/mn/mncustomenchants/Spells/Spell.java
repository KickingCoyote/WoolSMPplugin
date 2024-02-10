package pl.mn.mncustomenchants.Spells;

import org.bukkit.entity.Entity;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;

public abstract class Spell {


    public String name;
    public int cooldown;
    public Entity caster;

    public abstract void onTick();

    public abstract void onCast();

    public abstract void onHit();

    public abstract void cancel();

    public abstract void onEnd();



}
