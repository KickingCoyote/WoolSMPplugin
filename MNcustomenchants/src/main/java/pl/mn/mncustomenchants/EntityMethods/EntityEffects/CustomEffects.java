package pl.mn.mncustomenchants.EntityMethods.EntityEffects;

import org.bukkit.Color;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.main;

import java.util.*;

public class CustomEffects {

    public static Map<LivingEntity, Integer> stunned = new HashMap<>();
    public static Map<LivingEntity, Integer> frozen = new HashMap<>();


    //vector is x = ticks, y = level, z is empty
    public static Map<LivingEntity, Vector> decaying = new HashMap<>();
    //vector is x = ticks, y = inferno lvl, z is empty
    public static Map<LivingEntity, Vector> burning = new HashMap<>();

    //The int is the lvl
    public static Map<LivingEntity, Integer> regenerating = new HashMap<>();

    private static boolean isRunningEffectTimer = false;

    private static void startEffectTimer(){
        BukkitTask runnable = new BukkitRunnable() {

            int t = 1;

            @Override
            public void run() {

                isRunningEffectTimer = true;
                if (!stunned.isEmpty()){
                    stunned();
                }
                if (!frozen.isEmpty()){
                    frozen();
                }

                t++;
                if(!decaying.isEmpty() && t%20 == 0){
                    decaying();
                }
                if (!burning.isEmpty() && t%20 == 0){
                    burning();
                }
                if(!regenerating.isEmpty() && t%5 == 0){
                    regenerating();
                }

                if(t > 1000000){
                    t = 1;
                }

                if(stunned.isEmpty() && frozen.isEmpty() && decaying.isEmpty() && regenerating.isEmpty() && burning.isEmpty()){
                    isRunningEffectTimer = false;
                    this.cancel();

                }

            }
        }.runTaskTimer(main.getInstance(), 0, 1);


    }


    public static void stun(LivingEntity entity, int ticks){

        if (stunned.get(entity) != null){
            stunned.remove(entity);
        }


        //Particles
        Particles.ring(entity, main.getInstance(), Color.ORANGE, ticks, 0.5, 0.2, entity.getEyeLocation().add(0, 0.5, 0));


        if (EntityUtils.containsAttribute(entity, Attribute.GENERIC_MOVEMENT_SPEED)){

            if (EntityUtils.hasAttributeModifer(entity, Attribute.GENERIC_MOVEMENT_SPEED, "STUNNED_MODIFIER")){

                EntityUtils.detachAttributeMod(entity, Attribute.GENERIC_MOVEMENT_SPEED, "STUNNED_MODIFIER");
            }

            EntityUtils.attachAttributeMod(entity, Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier("STUNNED_MODIFIER", -1, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
        }

        stunned.put(entity, ticks);
        if(!isRunningEffectTimer){
            startEffectTimer();
        }
    }

    public static void freeze(LivingEntity entity, int ticks, int level){
        if (frozen.get(entity) != null){
            frozen.remove(entity);
        }

        //Particles


        if (EntityUtils.containsAttribute(entity, Attribute.GENERIC_MOVEMENT_SPEED)){

            if (EntityUtils.hasAttributeModifer(entity, Attribute.GENERIC_MOVEMENT_SPEED, "FROZEN_MODIFIER")){

                EntityUtils.detachAttributeMod(entity, Attribute.GENERIC_MOVEMENT_SPEED, "FROZEN_MODIFIER");
            }

            EntityUtils.attachAttributeMod(entity, Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier("FROZEN_MODIFIER", level*-0.1, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
        }
        frozen.put(entity, ticks);
        if(!isRunningEffectTimer){
            startEffectTimer();
        }

    }

    public static void decay(LivingEntity entity, int ticks, int level){
        if (decaying.get(entity) != null){
            decaying.remove(entity);
        }


        decaying.put(entity, new Vector(ticks, level, 0));

        if(!isRunningEffectTimer){
            startEffectTimer();
        }
    }

    //the bool is for if the effect should be added / removed
    public static void regen(LivingEntity entity, int level, Boolean remove)
    {
        if (remove && regenerating.get(entity) != null){
            regenerating.remove(entity);
        }

        if(!remove && regenerating.get(entity) == null){

            regenerating.put(entity, level);

            if(!isRunningEffectTimer){
                startEffectTimer();
            }
        }


    }

    public static void burn(LivingEntity entity, int ticks, int inferno){
        if (burning.get(entity) != null){
            burning.remove(entity);
        }

        burning.put(entity, new Vector(ticks, inferno, 0));
        entity.setVisualFire(true);

        if(!isRunningEffectTimer){
            startEffectTimer();
        }
    }

    private static void burning(){

        Iterator<Map.Entry<LivingEntity, Vector>> burnIter = burning.entrySet().iterator();

        while (burnIter.hasNext()) {

            Map.Entry<LivingEntity, Vector> current = burnIter.next();

            if (current.getKey().isDead()) {
                burning.remove(current.getKey());
                break;
            }

            if (current.getValue().getX() == 0) {

                burning.remove(current.getKey());
                current.getKey().setVisualFire(false);
            } else {

                //The burning damage
                CustomDamage.damageEntity(current.getKey(), 1 + Math.pow(current.getValue().getY(), 0.95), EntityUtils.DamageType.FIRE);

                //Burn visuals goes here
                current.getKey().playHurtAnimation(0.1f);
                for (Player player : current.getKey().getLocation().getNearbyPlayers(10)){
                    if (current.getKey().getHurtSound() != null){
                        player.playSound(current.getKey(), current.getKey().getHurtSound(), SoundCategory.HOSTILE, 1f, 1);
                    }

                }

                current.setValue(new Vector(current.getValue().getX() - 20, current.getValue().getY(), 0));
                burning.replace(current.getKey(), current.getValue());
            }
        }
    }

    private static void stunned(){

        Iterator<Map.Entry<LivingEntity, Integer>> stunnedIter = stunned.entrySet().iterator();

        while (stunnedIter.hasNext()){

            Map.Entry<LivingEntity, Integer> current = stunnedIter.next();

            if (current.getKey().isDead()){
                stunned.remove(current.getKey());
                break;
            }

            if (current.getValue() == 0){
                EntityUtils.detachAttributeMod(current.getKey(), Attribute.GENERIC_MOVEMENT_SPEED, "STUNNED_MODIFIER");

                stunned.remove(current.getKey());

            } else {

                current.setValue(current.getValue() - 1);
                stunned.replace(current.getKey(), current.getValue());

                //Stun code
                current.getKey().setVelocity(new Vector(0,0,0));


            }

        }
    }
    private static void frozen(){

        Iterator<Map.Entry<LivingEntity, Integer>> frozenIter = frozen.entrySet().iterator();

        while (frozenIter.hasNext())
        {
            Map.Entry<LivingEntity, Integer> current = frozenIter.next();

            if (current.getKey().isDead()){
                frozen.remove(current.getKey());
                break;
            }

            if (current.getValue() == 0){
                //Removes frozen if its time
                EntityUtils.detachAttributeMod(current.getKey(), Attribute.GENERIC_MOVEMENT_SPEED, "FROZEN_MODIFIER");

                frozen.remove(current.getKey());

            } else {
                current.setValue(current.getValue() - 1);
                frozen.replace(current.getKey(), current.getValue());
            }
        }
    }

    private static void decaying(){
        Iterator<Map.Entry<LivingEntity, Vector>> decayIter = decaying.entrySet().iterator();

        while (decayIter.hasNext())
        {
            Map.Entry<LivingEntity, Vector> current = decayIter.next();

            if (current.getKey().isDead()){
                decaying.remove(current.getKey());
                break;
            }

            if (current.getValue().getX() == 0){

                decaying.remove(current.getKey());

            } else {

                //Damage
                CustomDamage.damageEntity(current.getKey(), current.getValue().getY(), EntityUtils.DamageType.MAGIC);



                //Visuals
                current.getKey().playHurtAnimation(0.1f);
                for (Player player : current.getKey().getLocation().getNearbyPlayers(10)){
                    if (current.getKey().getHurtSound() != null){
                        player.playSound(current.getKey(), current.getKey().getHurtSound(), SoundCategory.HOSTILE, 1.3f, 1);
                    }

                }



                current.setValue(new Vector(current.getValue().getX() - 20, current.getValue().getY(), 0));
                decaying.replace(current.getKey(), current.getValue());
            }
        }
    }

    private static void regenerating(){

        Iterator<Map.Entry<LivingEntity, Integer>> regenIter = regenerating.entrySet().iterator();

        while (regenIter.hasNext()) {
            Map.Entry<LivingEntity, Integer> current = regenIter.next();

            if (current.getKey().isDead()) {
                break;
            }

            double regenAmount = Math.sqrt(current.getValue()) / 12;

            if (current.getKey().getHealth() + regenAmount <= current.getKey().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() && current.getKey().getHealth() >= 1){
                current.getKey().setHealth(current.getKey().getHealth() + regenAmount);
            }

        }
    }

}
