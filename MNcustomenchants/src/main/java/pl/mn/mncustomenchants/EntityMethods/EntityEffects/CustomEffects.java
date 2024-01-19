package pl.mn.mncustomenchants.EntityMethods.EntityEffects;

import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.main;

import java.rmi.UnexpectedException;
import java.util.*;

public class CustomEffects {

    private static Map<LivingEntity, Integer> stunned = new HashMap<>();
    private static Map<LivingEntity, Integer> frozen = new HashMap<>();


    private static void startEffectTimer(){
        BukkitTask runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (!stunned.isEmpty()){
                    stunned();
                }

                if(stunned.isEmpty() /* && all other maps*/){
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

        stunned.put(entity, ticks);
        startEffectTimer();
    }
    public static void freeze(LivingEntity entity, int ticks, int level){
        if (frozen.get(entity) != null){
            frozen.remove(entity);
        }

        //Particles


        if (EntityClassifications.containsAttribute(entity, Attribute.GENERIC_MOVEMENT_SPEED)){
            EntityClassifications.attachAttributeMod(entity, Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier("FROZEN_MODIFIER", 0.9, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
        }
        frozen.put(entity, ticks);
        startEffectTimer();
    }


    private static void stunned(){

        Iterator<Map.Entry<LivingEntity, Integer>> stunnedIter = stunned.entrySet().iterator();

        while (stunnedIter.hasNext()){

            Map.Entry<LivingEntity, Integer> current = stunnedIter.next();


            if (current.getValue() == 0){
                stunned.remove(current.getKey());
            } else {

                current.setValue(current.getValue() - 1);
                stunned.replace(current.getKey(), current.getValue());

                //Stun code
                current.getKey().setVelocity(new Vector(0,0,0));


            }

        }
    }



}
