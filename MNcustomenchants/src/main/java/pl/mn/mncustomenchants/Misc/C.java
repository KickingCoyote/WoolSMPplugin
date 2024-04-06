package pl.mn.mncustomenchants.Misc;


import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.EnumSet;
import java.util.List;

//Constants
public class C {


    public static List<Material> lightPassThroughBlocks = List.of(
            Material.GLASS,
            Material.GLASS_PANE,
            Material.WHITE_STAINED_GLASS,
            Material.ORANGE_STAINED_GLASS,
            Material.MAGENTA_STAINED_GLASS,
            Material.LIGHT_BLUE_STAINED_GLASS,
            Material.YELLOW_STAINED_GLASS,
            Material.LIME_STAINED_GLASS,
            Material.PINK_STAINED_GLASS,
            Material.GRAY_STAINED_GLASS,
            Material.LIGHT_GRAY_STAINED_GLASS,
            Material.CYAN_STAINED_GLASS,
            Material.PURPLE_STAINED_GLASS,
            Material.BLUE_STAINED_GLASS,
            Material.BROWN_STAINED_GLASS,
            Material.GREEN_STAINED_GLASS,
            Material.RED_STAINED_GLASS,
            Material.BLACK_STAINED_GLASS,
            Material.WHITE_STAINED_GLASS_PANE,
            Material.ORANGE_STAINED_GLASS_PANE,
            Material.MAGENTA_STAINED_GLASS_PANE,
            Material.LIGHT_BLUE_STAINED_GLASS_PANE,
            Material.YELLOW_STAINED_GLASS_PANE,
            Material.LIME_STAINED_GLASS_PANE,
            Material.PINK_STAINED_GLASS_PANE,
            Material.GRAY_STAINED_GLASS_PANE,
            Material.LIGHT_GRAY_STAINED_GLASS_PANE,
            Material.CYAN_STAINED_GLASS_PANE,
            Material.PURPLE_STAINED_GLASS_PANE,
            Material.BLUE_STAINED_GLASS_PANE,
            Material.BROWN_STAINED_GLASS_PANE,
            Material.GREEN_STAINED_GLASS_PANE,
            Material.RED_STAINED_GLASS_PANE,
            Material.BLACK_STAINED_GLASS_PANE,
            Material.AIR,
            Material.WATER
    );

    public  static  List<Vector> xyRelativeAdjacent = List.of(
            new Vector(1, 1, 0),
            new Vector(0, 1, 0),
            new Vector(1, 0, 0),
            new Vector(-1,-1,0),
            new Vector(-1,0, 0),
            new Vector(0, -1,0),
            new Vector(-1,1, 0),
            new Vector(1, -1,0)
    );


    public static final EnumSet<EntityType> FlyingMobs = EnumSet.of(
            EntityType.BEE,
            EntityType.VEX,
            EntityType.ALLAY,
            EntityType.BAT,
            EntityType.WITHER,
            EntityType.BLAZE,
            EntityType.PARROT,
            EntityType.PHANTOM,
            EntityType.GHAST
    );


}
