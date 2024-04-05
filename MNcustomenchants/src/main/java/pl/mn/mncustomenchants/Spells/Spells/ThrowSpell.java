package pl.mn.mncustomenchants.Spells.Spells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.Spells.Spell;
import pl.mn.mncustomenchants.Spells.SpellBases.LaunchSpellBase;
import pl.mn.mncustomenchants.Spells.SpellManager;

import java.util.ArrayList;
import java.util.List;

public class ThrowSpell extends LaunchSpellBase {

    int t;
    int bounces;
    double damage;
    Spell spell;
    List<Entity> targets;

    /**
     * Throws an entity, using with a non-full block falling block can cause problems
     * @param caster       caster
     * @param castLocation cast location
     * @param entity       the launched entity
     * @param direction    the launch direction
     * @param speed        launch velocity
     * @param damage       the damage on hit
     * @param hitSpell     casts a spell on hit, null does nothing
     */
    public ThrowSpell(LivingEntity caster, Location castLocation, Entity entity, Vector direction, double speed, double damage, Spell hitSpell) {
        super(caster, castLocation, entity, direction, speed);

        this.damage = damage;
        this.spell = hitSpell;

        t = 0;
        bounces = 0;
    }


    @Override
    public void onTick() {


        //TODO: if a player is launched they don't need to be grounded between casts, this might need to be changed
        t++;

        if (t > 1){

            targets = new ArrayList<>(entity.getNearbyEntities(1, 1, 1));
            targets.remove(caster);

            if (!targets.isEmpty()){
                onHit();
            }

            //If it hits ground
            if(entity.getWorld().getBlockAt(entity.getLocation().subtract(0, 0.6, 0)).getType() != Material.AIR){
                onHit();
            }
            //Cancel if the player doesn't touch the ground in a minute after cast
            if (t > 1200){
                onEnd();
            }
        }


    }

    @Override
    public void onHit() {


        //Damage Entity or Cast new spell
        spell.castLocation = entity.getLocation();
        SpellManager.castSpell(spell);

        for (Entity e : targets){

            if (e instanceof LivingEntity){
                CustomDamage.damage((LivingEntity) e, caster, damage, EntityUtils.DamageType.MAGIC);
            }
        }


        /*
        entity.setVelocity(entity.getVelocity().multiply(new Vector(1, -1, 1)));
        bounces++;
        t = 0;
        if (bounces > 0){
            entity.remove();
            onEnd();
        }
         */

        if (entity instanceof FallingBlock){

            if (entity.getWorld().getType(entity.getLocation()) == ((FallingBlock) entity).getBlockData().getMaterial()){
                entity.getWorld().setType(entity.getLocation(), Material.AIR);
            }

        }

        entity.remove();
        onEnd();


    }

    @Override
    public void cancel() {

    }
}
