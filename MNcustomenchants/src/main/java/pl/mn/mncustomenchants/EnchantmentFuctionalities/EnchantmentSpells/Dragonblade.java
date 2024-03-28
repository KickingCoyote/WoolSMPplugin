package pl.mn.mncustomenchants.EnchantmentFuctionalities.EnchantmentSpells;

import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.Spells.SpellManager;
import pl.mn.mncustomenchants.Spells.Spells.TeleportSpell;
import pl.mn.mncustomenchants.Spells.Spells.VolleySpell;
import pl.mn.mncustomenchants.main;

import java.util.Random;

public class Dragonblade implements Listener {

    public Dragonblade(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }


    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){

        if(!(event.getDamager() instanceof Player)) { return; }
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) { return; }

        if (!EntityUtils.isPlayerWithEnch(CustomEnchantments.dragon_blade, event.getDamager(), EquipmentSlot.HAND)) { return; }


        event.getDamager().setVelocity(event.getDamager().getLocation().toVector().subtract(event.getEntity().getLocation().toVector()).multiply(-1));

        CustomEffects.freeze((LivingEntity) event.getDamager(), 20, -10);


        //Particles n Sound


        ((Player) event.getDamager()).playSound(event.getEntity(), Sound.ENTITY_WARDEN_ATTACK_IMPACT, SoundCategory.PLAYERS,  1 , 1);



        Bukkit.getScheduler().runTaskLater(main.getInstance(), () -> {



            Location entityCenter = event.getEntity().getLocation().toVector().getMidpoint(((LivingEntity)event.getEntity()).getEyeLocation().toVector()).toLocation(event.getDamager().getWorld());


            for (int i = 0; i < 2; i++){
                Random random = new Random();

                double x = random.nextDouble() - 0.5;
                double y = random.nextDouble() - 0.5;
                double z = random.nextDouble() - 0.5;


                Vector offset = new Vector(x,y,z);

                offset.normalize();

                offset.multiply(2.5);

                Location a = entityCenter.clone().add(offset);
                Location b = entityCenter.clone().subtract(offset);

                ParticleData data = new ParticleData(
                        20,
                        Particle.REDSTONE,
                        Particles.line(a, b, 70, 3),
                        2,
                        entityCenter.toVector()

                );

                data.isDust = true;
                data.color = Color.PURPLE;

                Particles.RenderParticles(data, event.getDamager().getWorld());
            }


            if (event.getEntity().isDead()){
                ParticleData data = new ParticleData(
                        20,
                        Particle.REDSTONE,
                        Particles.sphereExplosion(entityCenter, 200, 1.5),
                        1,
                        entityCenter.toVector()

                );

                data.isDust = false;
                data.color = Color.WHITE;

                Particles.RenderParticles(data, event.getDamager().getWorld());

            }




        }, 7);






    }

}
