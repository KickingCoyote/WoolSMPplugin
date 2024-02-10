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


    int cooldown;
    Location spawnLocation;
    Vector direction;
    double speed;

    float power;
    double range;
    SmallFireball fireball;
    double damage;

    List<Entity> targets;

    public FireballSpell(Entity caster, int cooldown, Location spawnLocation, Vector direction, int speed, float power, double range, double damage){
        this.caster = caster;

        this.cooldown = cooldown;
        this.spawnLocation = spawnLocation;
        this.direction = direction;
        this.speed = speed;
        this.power = power;
        this.range = range;
        this.damage = damage;

        targets = new ArrayList<>();
    }


    @Override
    public void onTick() {

        if (spawnLocation.toVector().distance(fireball.getLocation().toVector()) > range){

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
        fireball = (SmallFireball) caster.getWorld().spawnEntity(spawnLocation, EntityType.SMALL_FIREBALL);

        fireball.setVelocity(direction.multiply(speed));

        fireball.setShooter((ProjectileSource) caster);

        fireball.setIsIncendiary(false);

        fireball.setYield(power);

    }

    @Override
    public void onHit() {


        caster.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, fireball.getLocation().add(0, 0.7, 0), 2);
        caster.playSound(Sound.sound().type(org.bukkit.Sound.ENTITY_GENERIC_EXPLODE).build());

        for (Entity e : targets){

            if (e instanceof LivingEntity){

                CustomEffects.burn((LivingEntity) e, 60, 3);

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
