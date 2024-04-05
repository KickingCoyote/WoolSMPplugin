package pl.mn.mncustomenchants.Abilities;

import io.papermc.paper.event.player.PlayerArmSwingEvent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.Spells.Spell;
import pl.mn.mncustomenchants.Spells.SpellBases.RadialAoeSpellBase;
import pl.mn.mncustomenchants.Spells.SpellManager;
import pl.mn.mncustomenchants.Spells.Spells.MagicBoltSpell;
import pl.mn.mncustomenchants.Spells.Spells.ThrowSpell;
import pl.mn.mncustomenchants.Spells.Spells.VolleySpell;
import pl.mn.mncustomenchants.main;

import java.util.List;

public class AbilityManager implements Listener {

    public AbilityManager(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }


    @EventHandler
    public void onClick(PlayerInteractEvent event){

        if(!(event.getPlayer().isSneaking() && event.getAction() == Action.LEFT_CLICK_AIR)){
            return;
        }

        /*
        ParticleData particleData = new ParticleData(
                4,
                Particle.REDSTONE,
                Particles.sphere(event.getPlayer().getEyeLocation(), 100, 1),
                2,
                event.getPlayer().getLocation().toVector()
        );
        particleData.isDust = true;
        particleData.color = Color.RED;
        particleData.particleSize = 0.3f;

        Spell s = new MagicBoltSpell(event.getPlayer(), event.getPlayer().getEyeLocation(), event.getPlayer().getLocation().getDirection(), 0.5, particleData, 20, 0.07, 5, List.of(event.getPlayer()));

        SpellManager.castSpell(s);

         */

    }

}
