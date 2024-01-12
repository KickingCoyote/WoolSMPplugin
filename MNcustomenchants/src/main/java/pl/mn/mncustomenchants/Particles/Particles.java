package pl.mn.mncustomenchants.Particles;

import com.destroystokyo.paper.ParticleBuilder;
import org.apache.logging.log4j.message.Message;
import org.bukkit.*;
import org.bukkit.entity.Entity;
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



    public static void ring(Entity entity, Plugin plugin, Color color, int ticks, double radius, double speed, Location location){


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
    //for p to function well p should be a factor of t.
    //color and size is only used if particle is REDSTONE.
    private static void RenderParticles(Entity entity, List<Location> locations, int ticks, int p, Particle particle, Particle.DustOptions dustOptions, int period, int delay){




        //Should the particles move with the entity
        boolean moveWithEntity = false;

        //If the particles are stationary, should they be set before or after the delay.
        boolean setLocationAfterDelay = true;

        //If effect is spawned at entity
        boolean EntityBased = false;

        new BukkitRunnable() {

            Location Loc = entity.getLocation();
            List<Location> activeLocations = new ArrayList<Location>();
            int t = 1;


            @Override
            public void run() {

                if((setLocationAfterDelay && t == 1) || moveWithEntity){
                    Loc = entity.getLocation();
                }


                if (t <= p){
                    for (int i = 0; i < t*(locations.size() / p); i++){
                        if (!activeLocations.contains(locations.get(i))){activeLocations.add(locations.get(i));}
                    }
                }

                t += period;


                for (Location l : activeLocations){
                    for (Player player : l.getWorld().getPlayers()){

                        if (player.getLocation().distanceSquared(l.clone().add(Loc)) < 1000){
                            if (particle.equals(Particle.REDSTONE)){
                                player.spawnParticle(particle, l.clone().add(Loc), 1, dustOptions);
                            }
                            else {
                                player.spawnParticle(particle, l.clone().add(Loc), 1);
                            }
                        }
                    }
                }

                if (t > ticks){this.cancel();}

            }

        }.runTaskTimer(main.getInstance(), delay, period);

    }


    public static void spiral (Entity entity, Location location){

        List<Location> locations = new ArrayList<Location>();


        double r = 0;
        double x, y, z;

        //How much y changes over time : 0
        double yMod = 0;

        //Lower value = more dens : 0.1
        double particleDensity = 0.1;

        // : 2 = small, 4/5 = big
        double size = 5;

        //If it is a double or a simple Archimedes spiral : false
        boolean Dual = true;

        //how many circles : 6
        double rotations = 6;

        


        for (double theta = Math.PI * rotations; theta > 0; theta -= particleDensity / size){

            //sets vector to pos relative to entity.location
            Vector vector = location.toVector().subtract(entity.getLocation().toVector());


            //Main mathematical function
            r = size * particleDensity * theta;


            //Convert polar coordinates to cartesian coordinates
            x = r * Math.cos(theta);
            z = r * Math.sin(theta);
            y = theta * yMod;


            vector.add(new Vector(x,  y, z));

            locations.add(vector.toLocation(entity.getWorld()));


            //Dual Spiral
            if (Dual){
                vector = location.toVector().subtract(entity.getLocation().toVector());

                x = -1 * r * Math.cos(theta);
                z = -1 * r * Math.sin(theta);

                vector.add(new Vector(x,  y, z));

                locations.add(vector.toLocation(entity.getWorld()));
            }



        }

        RenderParticles(entity,locations, 120, 80, Particle.DRIP_LAVA, new Particle.DustOptions(Color.AQUA, 0.6f), 2, 40);

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
