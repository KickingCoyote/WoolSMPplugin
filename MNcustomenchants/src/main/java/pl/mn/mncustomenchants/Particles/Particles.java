package pl.mn.mncustomenchants.Particles;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Particles {



    public static void headRing(LivingEntity entity, Plugin plugin, Color color, int ticks, double r, double speed){



        Particle particle = Particle.REDSTONE;
        Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1f);

        new BukkitRunnable() {

            int t = 0;

            public void run(){
                t++;
                double x =  r *Math.sin(t * speed);
                double z = r * Math.cos(t * speed);
                Location location = entity.getLocation().add(0, 2, 0);
                location.add(x, 0, z);

                for (Player player : location.getWorld().getPlayers()){
                    if (player.getLocation().distanceSquared(location) < 250){
                        player.spawnParticle(particle, location, 1, dustOptions);
                    }
                }

                if (t > ticks){
                    this.cancel();
                }
            }

        }.runTaskTimer(plugin, 0, 1);

    }

}
