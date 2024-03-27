package pl.mn.mncustomenchants;

import org.bukkit.util.Vector;

public class MathUtils {


    public static Vector Lerp(Vector a, Vector b, double t){


        Vector l = new Vector(0,0,0);

        l.setY(a.getY() * (1.0 - t) + (b.getY() * t));
        l.setX(a.getX() * (1.0 - t) + (b.getX() * t));
        l.setZ(a.getZ() * (1.0 - t) + (b.getZ() * t));


        return l;
    }

    public static Vector Slerp(Vector a, Vector b, double t, double power){


        t = Math.pow(2, power - 1) * Math.pow(t - 0.5, power) + 0.5;

        Vector l = new Vector(0,0,0);

        l.setY(a.getY() * (1.0 - t) + (b.getY() * t));
        l.setX(a.getX() * (1.0 - t) + (b.getX() * t));
        l.setZ(a.getZ() * (1.0 - t) + (b.getZ() * t));


        return l;
    }

}
