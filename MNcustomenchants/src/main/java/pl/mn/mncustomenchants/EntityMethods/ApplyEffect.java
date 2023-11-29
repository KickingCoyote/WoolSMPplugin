package pl.mn.mncustomenchants.EntityMethods;

import org.bukkit.Bukkit;
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
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.StunEffect;
import pl.mn.mncustomenchants.main;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ApplyEffect {


    public static void Stun(Mob entity, int duration, Plugin plugin){

        StunEffect stunEffect = new StunEffect(entity);

        EntityClassifications.activeEffects.add(stunEffect);

        BukkitTask task = stunEffect.runTaskLater(plugin, duration);

    }

    public static void Decay(LivingEntity entity, int frequency, Plugin plugin){

        DecayEffect decayEffect = new DecayEffect(entity, frequency);

        BukkitTask task;

        List<EntityEffect> entityEffects = EntityClassifications.getActiveEffect(entity);

        for (EntityEffect e : entityEffects){
            if (e instanceof DecayEffect) {
                e.cancel();
                EntityClassifications.activeEffects.remove(e);
            }
        }

        EntityClassifications.activeEffects.add(decayEffect);
        decayEffect.runTaskTimer(plugin, frequency, frequency);



    }




}
