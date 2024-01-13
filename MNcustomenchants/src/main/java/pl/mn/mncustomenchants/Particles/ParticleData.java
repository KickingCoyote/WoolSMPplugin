package pl.mn.mncustomenchants.Particles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Vex;
import org.bukkit.util.Vector;

import java.time.Period;
import java.util.List;

public class ParticleData {

    public boolean isEntityBased;
    public boolean isDust;
    public boolean moveWithEntity;
    public boolean setLocationAfterDelay;



    public float particleSize;
    public Color color;

    public int period;
    public int delay;
    public Particle particle;

    //used as relative 0 0 if entity based.
    public Entity entity;

    //used instead of entity if not entity based.
    public org.bukkit.util.Vector worldPos;

    public org.bukkit.util.Vector location;

    //Locations for all the particles.
    public List<org.bukkit.util.Vector> locations;

    public int ticks;

    public int p;

    public ParticleData(int ticks, Particle particle, List<Vector> locations, int period, Entity entity, boolean moveWithEntity){
        this.ticks = ticks;
        this.particle = particle;
        this.locations = locations;
        this.period = period;
        this.entity = entity;
        this.moveWithEntity = moveWithEntity;
        location = entity.getLocation().toVector();
        p = 1;
        delay = 0;
        color = Color.WHITE;
        particleSize = 1f;
        isDust = false;
        setLocationAfterDelay = false;

        //DO NOT CHANGE
        isEntityBased = true;
    }
    public ParticleData(int ticks, Particle particle, List<Vector> locations, int period, Vector worldPos){
        this.ticks = ticks;
        this.particle = particle;
        this.locations = locations;
        this.period = period;
        this.worldPos = worldPos;
        location = worldPos;
        p = 1;
        delay = 0;
        color = Color.WHITE;
        particleSize = 1f;
        isDust = false;

        //DO NOT CHANGE
        isEntityBased = false;
    }

}
