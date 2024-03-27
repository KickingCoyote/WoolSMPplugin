package pl.mn.mncustomenchants.Particles;

import com.destroystokyo.paper.ParticleBuilder;
import jdk.jshell.execution.LoaderDelegate;
import org.apache.logging.log4j.message.Message;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vex;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.MathUtils;
import pl.mn.mncustomenchants.main;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Random;

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



    public static List<Vector> sphere(Location location, double density, double radius){

        List<Vector> locations = new ArrayList<>();
        Random r = new Random();



        for (int i = 0; i < density; i++){

            double x = r.nextDouble() - 0.5;
            double y = r.nextDouble() - 0.5;
            double z = r.nextDouble() - 0.5;

            Vector v = new Vector(x,y,z);
            v.normalize();

            v.multiply(radius);

            locations.add(location.toVector().clone().add(v));

        }



        return locations;
    }


    public static List<Vector> sphereExplosion(Location location, double density, double radius){

        List<Vector> locations = new ArrayList<>();
        Random r = new Random();



        for (int i = 0; i < density; i++){

            double x = r.nextDouble() - 0.5;
            double y = r.nextDouble() - 0.5;
            double z = r.nextDouble() - 0.5;

            Vector v = new Vector(x,y,z);
            v.normalize();

            v.multiply(i * radius / density);

            locations.add(location.toVector().clone().add(v));

        }



        return locations;
    }

    public static List<Vector> line (Location a, Location b, double density){
        return line(a, b, density, 1);
    }

    /**
     *
     * @param a location 1
     * @param b location 2
     * @param density total amount of locations
     * @param slerpPower the density distribution of the line, takes in odd integers, 1 is linear and higher numbers focuses the density towards the center
     * @return locations list
     */
    public static List<Vector> line (Location a, Location b, double density, double slerpPower){


        List<Vector> locations = new ArrayList<>();

        for (double i = 0; i <= density; i++){

            Vector v;

            v = MathUtils.Slerp(a.toVector(), b.toVector(), i / density, slerpPower);


            locations.add(v);
        }



        return locations;
    }



    /**
     *
     * @param location the tip of the cone
     * @param direction cast direction
     * @param density how many locations in total
     * @param radius radius
     * @param angle the cone angle in radians
     * @param densityDistribution the distribution of the particles, lower numbers are more towards the edge, higher towards the center, 0.5 is approximately equal
     * @return location list
     */

    public static List<Vector> cone(Location location, Vector direction, double density, double radius, double angle, double densityDistribution){

        List<Vector> locations = new ArrayList<>();

        Random r = new Random();
        direction.normalize();


        for (double i = 0; i < density; i++){

            Vector v = direction.clone();
            v.rotateAroundY((r.nextDouble() -0.5) * angle);
            v.multiply(Math.pow(i / density, densityDistribution) * radius);

            locations.add(v);
        }





        return locations;
    }



    public static List<Vector> spiral (Location location, double yMod, double particleDensity, double size, boolean dual, double rotations){

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
        Vector vector = location.toVector();

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
