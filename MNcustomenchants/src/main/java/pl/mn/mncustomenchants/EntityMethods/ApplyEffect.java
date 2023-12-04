package pl.mn.mncustomenchants.EntityMethods;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.Decay;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.DecayEffect;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.EntityEffect;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.RegenerationEffect;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.StunEffect;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.main;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ApplyEffect {


    public static void Stun(LivingEntity entity, int duration, Plugin plugin){

        StunEffect stunEffect = new StunEffect(entity);
        Particles.headRing(entity, plugin, Color.ORANGE, duration, 0.5, 0.5);

        EntityClassifications.activeEffects.add(stunEffect);

        stunEffect.runTaskLater(plugin, duration);

    }
    public static void Slow(LivingEntity entity, int duration, Plugin plugin){

    }


    public static void Decay(LivingEntity entity, int frequency, Plugin plugin){



        DecayEffect decayEffect = new DecayEffect(entity, frequency);

        List<EntityEffect> entityEffects = EntityClassifications.getActiveEffect(entity);

        for (EntityEffect e : entityEffects){
            if (e instanceof DecayEffect) {
                e.cancel();
                EntityClassifications.activeEffects.remove(e);
            }
        }

        EntityClassifications.activeEffects.add(decayEffect);
        decayEffect.runTaskTimer(plugin, 16, 16);

    }
    public static void Regeneration (LivingEntity entity, int level, Plugin plugin, boolean isActive){



        List<EntityEffect> entityEffects = EntityClassifications.getActiveEffect(entity);


        for (EntityEffect e : entityEffects){
            if (e instanceof RegenerationEffect) {
                e.cancel();
                EntityClassifications.activeEffects.remove(e);
            }
        }

        if (isActive){
            RegenerationEffect regenerationEffect = new RegenerationEffect(entity, level);

            EntityClassifications.activeEffects.add(regenerationEffect);

            regenerationEffect.runTaskTimer(plugin, 0, 5);
        }




    }




}
