package pl.mn.mncustomenchants.Particles;

import com.destroystokyo.paper.ParticleBuilder;
import org.apache.logging.log4j.message.Message;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vex;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.main;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;

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





    // p is an int value between 1 and t that represents how many ticks it will take for the effect to be fully rendered.
    //color and size is only used if particle is REDSTONE.
    private static void RenderParticles(LivingEntity entity, List<Location> locations, Color color, int ticks, int p, Particle particle, float size){


        Particle.DustOptions dustOptions = new Particle.DustOptions(color, size);


        new BukkitRunnable() {


            List<Location> activeLocations = new ArrayList<Location>();
            int t = 0;


            @Override
            public void run() {

                t += 3;


                for (int i = 0; i < locations.size() / p; i++){

                    activeLocations.add(locations.get(i));
                }



                for (Location l : activeLocations){
                    for (Player player : l.getWorld().getPlayers()){

                        if (player.getLocation().distanceSquared(l.clone().add(entity.getLocation())) < 250){
                            if (particle.equals(Particle.REDSTONE)){
                                player.spawnParticle(particle, l.clone().add(entity.getLocation()), 1, dustOptions);
                            }
                            else {
                                player.spawnParticle(particle, l.clone().add(entity.getLocation()), 1);
                            }
                        }
                    }
                }

                if (t > ticks){
                    this.cancel();
                }

            }

        }.runTaskTimer(main.getInstance(), 5, 3);

    }


    public static void spiral (LivingEntity entity, Location location){

        List<Location> locations = new ArrayList<Location>();



        double r = 0;
        double z = 0;
        double x = 0;

        for (double pheta = 0; pheta < Math.PI * 6; pheta += 0.1){

            Vector vector = location.toVector().subtract(entity.getLocation().toVector());

            r = 0.1 * pheta;

            x = r * Math.cos(pheta);
            z = r * Math.sin(pheta);

            vector.add(new Vector(x,  0, z));

            locations.add(vector.toLocation(entity.getWorld()));


        }


        RenderParticles(entity,locations, Color.AQUA, 60, 1, Particle.REDSTONE, 0.4f);

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
