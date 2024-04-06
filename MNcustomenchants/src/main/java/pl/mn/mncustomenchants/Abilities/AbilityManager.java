package pl.mn.mncustomenchants.Abilities;

import io.papermc.paper.event.player.PlayerArmSwingEvent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.Spells.Spell;
import pl.mn.mncustomenchants.Spells.SpellBases.RadialAoeSpellBase;
import pl.mn.mncustomenchants.Spells.SpellManager;
import pl.mn.mncustomenchants.Spells.Spells.*;
import pl.mn.mncustomenchants.main;

import java.util.ArrayList;
import java.util.List;

public class AbilityManager implements Listener {

    public AbilityManager(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }


    @EventHandler
    public void onClick(PlayerInteractEvent event){

        if(!(event.getPlayer().isSneaking() && event.getAction().isRightClick() && event.getHand() == EquipmentSlot.HAND)){
            return;
        }



        ParticleData particleData = new ParticleData(
                4,
                Particle.REDSTONE,
                Particles.sphere(event.getPlayer().getEyeLocation(), 100, 1),
                2,
                event.getPlayer().getLocation().toVector()
        );
        particleData.isDust = true;
        particleData.color = Color.AQUA;
        particleData.particleSize = 0.5f;

        Spell s = new SeekerLaserSpell(event.getPlayer(), event.getPlayer().getEyeLocation(), 10, 0, 0, particleData, true, 12, List.of(event.getPlayer()));


        /*
        List<Entity> t = event.getPlayer().getNearbyEntities(10, 10, 10);
        t.remove(event.getPlayer());
        for (Entity e : new ArrayList<>(t)){
            if (e instanceof LivingEntity){continue;}
            t.remove(e);
        }
        if(t.isEmpty()){return;}
        Spell s2 = new HomingBoltSpell(
                (LivingEntity) t.get(0), ((LivingEntity)t.get(0)).getEyeLocation(),
                event.getPlayer(), 0.2,
                particleData, 30, 0.5,
                List.of((LivingEntity) t.get(0)), 10
        );

         */

        SpellManager.castSpell(s);






    }

}
