package pl.mn.mncustomenchants;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
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


    /**
     *
     * Checks if location A is inside semi cylinder based around location B
     */
    public static boolean CyclicCollisionDetection(Location A, Location B, double radius, double angle, double height, double depth) {

        if(A.getY() - B.getY() > height || B.getY() - A.getY() > depth) {return false;}


        Vector APos = A.toVector().setY(0);
        Vector BDir = B.getDirection().setY(0).normalize();
        Vector BPos = B.toVector().setY(0);
        Vector ARelative = APos.clone().subtract(BPos).normalize();


        if (APos.distance(BPos) > radius) {return false;}


        if (Math.acos(ARelative.dot(BDir)) > angle / 2) {
            return false;
        }

        return true;
    }

}
