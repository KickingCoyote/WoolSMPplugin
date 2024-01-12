package pl.mn.mncustomenchants.Particles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.time.Period;
import java.util.List;

public class ParticleData {

    public boolean isEntityBased;
    public boolean isDust;
    public boolean moveWithEntity;
    public boolean setLocationAfterDelay;



    public float size;
    public Color color;

    public int period;
    public int delay;
    public Particle particle;

    //used as relative 0 0 if entity based.
    public LivingEntity entity;

    //used instead of entity if not entity based.
    public Vector worldPos;

    public List<Location> locations;

    public int ticks;

    public int p;

    public ParticleData(int ticks, Particle particle, List<Location> locations, int period){
        this.ticks = ticks;
        this.particle = particle;
        this.locations = locations;
        this.period = period;
        p = 1;
        delay = 0;
        color = Color.WHITE;
        size = 1f;
        isDust = false;
        isEntityBased = true;
        moveWithEntity = true;
        setLocationAfterDelay = false;
        worldPos = new Vector(0,0,0);
    }

}
