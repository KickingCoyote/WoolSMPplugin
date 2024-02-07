package pl.mn.mncustomenchants.ItemMethods;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import pl.mn.mncustomenchants.main;

import java.util.List;

public class AttributeType implements Comparable<AttributeType>{

    private String name;
    private String showName;

    public AttributeType(String name, String showName){
        this.name = name;
        this.showName = showName;
    }

    public String getName(){
        return name;
    }

    public String getShowName() {
        return showName;
    }

    public String ToString(){
        return name;
    }


    public static AttributeType valueOf(String s){

        for (AttributeType at : values){

            if (at.name.equalsIgnoreCase(s)){
                return at;
            }

        }

        return null;
    }


    public static final AttributeType THORNS = new AttributeType("THORNS",  "Thorns");
    public static final AttributeType ARMOR = new AttributeType("ARMOR",  "Armor");
    public static final AttributeType SPEED = new AttributeType("SPEED", "Speed");
    public static final AttributeType ATTACK_SPEED = new AttributeType("ATTACK_SPEED", "Attack Speed");
    public static final AttributeType ATTACK_DAMAGE = new AttributeType("ATTACK_DAMAGE", "Attack Damage");
    public static final AttributeType HEALTH = new AttributeType("HEALTH", "Max Health");
    public static final AttributeType PROJECTILE_DAMAGE = new AttributeType("PROJECTILE_DAMAGE", "Projectile Damage");
    public static final AttributeType PROJECTILE_SPEED = new AttributeType("PROJECTILE_SPEED", "Projectile Speed");
    public static final AttributeType THROW_RATE = new AttributeType("THROW_RATE", "Throw Rate");
    public static final AttributeType KNOCKBACK_RESISTANCE = new AttributeType("KNOCKBACK_RESISTANCE", "Knockback Resistance");

    //They appear in the same order in-game as in the list
    public static final List<AttributeType> values = List.of(
            ARMOR,
            ATTACK_DAMAGE,
            ATTACK_SPEED,
            PROJECTILE_DAMAGE,
            PROJECTILE_SPEED,
            THROW_RATE,
            HEALTH,
            SPEED,
            KNOCKBACK_RESISTANCE,
            THORNS
    );

    @Override
    public int compareTo(AttributeType o) {
        return values.indexOf(this) - values.indexOf(o);
    }
}
