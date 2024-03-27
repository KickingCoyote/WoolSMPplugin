package pl.mn.mncustomenchants.Spells.Spells;

import net.kyori.adventure.sound.Sound;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.Spells.Spell;
import pl.mn.mncustomenchants.Spells.SpellManager;

import java.util.ArrayList;
import java.util.List;

public class FireballSpell extends Spell {


    double speed;

    double range;
    SmallFireball fireball;
    double damage;

    List<Entity> targets;

    public FireballSpell(LivingEntity caster, Location castLocation, int speed, double range, double damage){
        this.caster = caster;
        this.castLocation = castLocation;

        this.speed = speed;
        this.range = range;
        this.damage = damage;

        targets = new ArrayList<>();
    }


    @Override
    public void onTick() {

        if (castLocation.toVector().distance(fireball.getLocation().toVector()) > range){

            fireball.remove();
            onEnd();
        }

        if (!caster.getWorld().getEntities().contains(fireball)) {

            for (Entity e : fireball.getLocation().getNearbyEntities(3, 3, 3)) {

                if (e instanceof LivingEntity){
                    targets.add(e);
                }
            }

            onHit();

        }

        /*
        if (!fireball.getNearbyEntities(1, 1, 1).isEmpty()){

            for (Entity e : fireball.getNearbyEntities(1, 1, 1)){

                if (!(e.equals(caster) || e.equals(fireball)) && e instanceof Mob) {

                    for (Entity e2 : fireball.getNearbyEntities(5, 5, 5)){

                        if (!(e.equals(caster) || e.equals(fireball)) && e instanceof Mob) {

                            targets.add(e2);

                        }

                    }

                }

            }
            if (!targets.isEmpty()){

                onHit();

            }



        }*/






    }

    @Override
    public void onCast() {
        fireball = (SmallFireball) caster.getWorld().spawnEntity(castLocation, EntityType.SMALL_FIREBALL);

        fireball.setVelocity(castLocation.getDirection().multiply(speed));

        fireball.setShooter(caster);

        fireball.setIsIncendiary(false);

    }

    @Override
    public void onHit() {


        caster.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, fireball.getLocation().add(0, 0.7, 0), 2);
        caster.playSound(Sound.sound().type(org.bukkit.Sound.ENTITY_GENERIC_EXPLODE).build());

        for (Entity e : targets){

            if (e instanceof LivingEntity){

                CustomDamage.damage((LivingEntity) e, caster, damage, EntityUtils.DamageType.MAGIC, true, fireball);

            }

        }
        onEnd();


    }

    //Cancel is only ran from SpellManager.cancel()
    @Override
    public void cancel() {
        fireball.remove();
    }


    @Override
    public void onEnd() {
        SpellManager.removeSpell(this);

        fireball.remove();
    }
}
