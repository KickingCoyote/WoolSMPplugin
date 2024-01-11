package pl.mn.mncustomenchants.Particles;

import com.destroystokyo.paper.ParticleBuilder;
import org.apache.logging.log4j.message.Message;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Particles {



    public static void ring(LivingEntity entity, Plugin plugin, Color color, int ticks, double radius, double speed, Location location){



        Particle particle = Particle.REDSTONE;
        Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1f);

        Vector relative = location.toVector().subtract(entity.getLocation().toVector());

        new BukkitRunnable() {

            int t = 0;
            double r = radius;

            public void run(){
                t++;
                double x =  r *Math.sin(t * speed);
                double z = r * Math.cos(t * speed);
                Location location = entity.getLocation();

                location.add(relative);

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


    public static void sprial(LivingEntity entity, Plugin plugin, Color color, int ticks, double radius, double speed, Location location){



        Particle particle = Particle.REDSTONE;
        Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1f);

        Vector relative = location.toVector().subtract(entity.getLocation().toVector());

        new BukkitRunnable() {

            int t = 0;
            double r = radius;
            List<Location> list = new ArrayList<Location>();

            public void run(){
                t++;
                r += 0.1;
                double x =  r *Math.sin(t * speed);
                double z = r * Math.cos(t * speed);
                Location location = entity.getLocation();

                location.add(relative);
                location.add(x, 0, z);

                list.add(location);

                for (Player player : location.getWorld().getPlayers()){
                    if (player.getLocation().distanceSquared(location) < 250){
                        for (Location l : list){
                            player.spawnParticle(particle, l, 1, dustOptions);
                        }
                    }
                }

                if (t > ticks){
                    this.cancel();
                }
            }

        }.runTaskTimer(plugin, 0, 1);

    }

    public static void GroundExplosion (LivingEntity target, Plugin plugin, Color color, int ticks, double r, double speed){

        Particle particle = Particle.REDSTONE;
        Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1);

        for (Player player : target.getLocation().getWorld().getPlayers()){
            if (player.getLocation().distanceSquared(target.getLocation()) < 250){



            }
        }


    }

}
