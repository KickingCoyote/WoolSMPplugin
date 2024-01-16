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
    public static void RenderParticles(ParticleData pD, World world){




        new BukkitRunnable() {

            Vector Loc;

            List<Vector> activeLocations = new ArrayList<Vector>();
            int t = 1;


            @Override
            public void run() {

                if (t == 1){
                    if(pD.isEntityBased){
                        Loc = pD.entity.getLocation().toVector();
                    } else {
                        Loc = pD.worldPos;
                    }
                } else if (pD.isEntityBased && pD.moveWithEntity){
                    Loc = pD.entity.getLocation().toVector();
                }



                if (t <= pD.p){
                    for (int i = 0; i < t*(pD.locations.size() / pD.p); i++){
                        if (!activeLocations.contains(pD.locations.get(i)))
                        {
                            activeLocations.add(pD.locations.get(i));
                            spawnParticle(pD.particle, new Particle.DustOptions(pD.color, pD.particleSize), 1000, world, pD.locations.get(i));
                        }
                    }
                }

                t += 1;


                if (t%pD.period == 0){

                    for (Vector l : activeLocations){
                        spawnParticle(pD.particle, new Particle.DustOptions(pD.color, pD.particleSize), 1000, world, l.clone().add(Loc));
                    }

                }

                if (t > pD.ticks){this.cancel();}

            }

        }.runTaskTimer(main.getInstance(), pD.delay, 1);

    }

    private static void spawnParticle(Particle particle, Particle.DustOptions dustOptions, int distance2, World world, Vector vector){
        for (Player player : world.getPlayers()){

            if(player.getLocation().distanceSquared(vector.toLocation(world)) < distance2){
                if (particle == Particle.REDSTONE){
                    player.spawnParticle(particle, vector.toLocation(world), 1, dustOptions);
                } else {
                    player.spawnParticle(particle, vector.toLocation(world), 1);
                }

            }

        }

    }



    public static List<Vector> spiral (ParticleData pD, double yMod, double particleDensity, double size, boolean dual, double rotations){

        List<Vector> locations = new ArrayList<Vector>();


        double r = 0;
        double x, y, z;

        //NAME : USE : normal values

        //yMod : How much y changes over time : 0
        //particleDensity : Lower value = more dens : 0.1
        //size : 2 = small, 4/5 = big
        //dual : If it is a double or a simple Archimedes spiral : false
        //rotations : how many circles worth of rotations: 6


        //sets vector to pos relative to entity.location
        Vector vector = pD.location.subtract(pD.entity.getLocation().toVector());

        for (double theta = Math.PI * rotations; theta > 0; theta -= particleDensity / size){



            //Main mathematical function
            r = size * particleDensity * theta;


            //Convert polar coordinates to cartesian coordinates
            x = r * Math.cos(theta);
            z = r * Math.sin(theta);
            y = theta * yMod;



            locations.add(vector.clone().add(new Vector(x,y,z)));


            //Dual Spiral
            if (dual){

                x = -1 * r * Math.cos(theta);
                z = -1 * r * Math.sin(theta);

                locations.add(vector.clone().add(new Vector(x,y,z)));
            }



        }

        Bukkit.getPlayer("MN_128").sendMessage(locations.size() + "");
        return locations;
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
