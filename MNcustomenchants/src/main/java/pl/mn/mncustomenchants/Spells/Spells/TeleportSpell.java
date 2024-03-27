package pl.mn.mncustomenchants.Spells.Spells;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.Teleportation;
import pl.mn.mncustomenchants.Spells.Spell;
import pl.mn.mncustomenchants.Spells.SpellManager;

public class TeleportSpell extends Spell {


    private int delay;
    private float maxDistance;
    private Location targetLocation;


    public TeleportSpell(LivingEntity caster, Location targetLocation, int delay, float maxDistance){
        this.caster = caster;
        this.castLocation = caster.getEyeLocation();
        this.targetLocation = targetLocation;
        this.delay = delay;
        this.maxDistance = maxDistance;
    }


    @Override
    public void onTick() {

        if (delay == 0){

            onHit();

        } else {

            delay--;

        }
    }

    @Override
    public void onCast() {
        if (targetLocation.distance(castLocation) > maxDistance){
            onEnd();
        }
    }

    @Override
    public void onHit() {
        caster.teleport(targetLocation);
        onEnd();
    }

    @Override
    public void cancel() {

    }

    @Override
    public void onEnd() {
        SpellManager.removeSpell(this);
    }
}
