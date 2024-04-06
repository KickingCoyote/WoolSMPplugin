package pl.mn.mncustomenchants.Abilities.Magic;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.Spells.Spell;
import pl.mn.mncustomenchants.Spells.SpellManager;
import pl.mn.mncustomenchants.Spells.Spells.SeekerLaserSpell;

import java.util.List;

public class ManaLance {

    public static void temp(Player player){

        ParticleData particleData = new ParticleData(
                4,
                Particle.REDSTONE,
                Particles.sphere(player.getEyeLocation(), 100, 1),
                2,
                player.getLocation().toVector()
        );
        particleData.isDust = true;
        particleData.color = Color.AQUA;
        particleData.particleSize = 0.5f;

        Spell manaLance = new SeekerLaserSpell(player, player.getEyeLocation(), 10, 0, 0, particleData, true, 12, List.of(player));

        SpellManager.castSpell(manaLance);

    }


}
