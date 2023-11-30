package pl.mn.mncustomenchants.CustomEnchantments;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.checkerframework.checker.units.qual.Luminance;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class CustomEnchantments {

    public static Enchantment valueOf(String arg){
        if (arg.equals("radiant")){
            return radiant;
        }
        else if (arg.equals("thunder_aspect")){
            return thunder_aspect;
        }
        else if (arg.equals("decay")){
            return decay;
        }
        else if (arg.equals("recoil")){
            return recoil;
        }
        else if (arg.equals("two_handed")){
            return two_handed;
        }
        else if (arg.equals("quake")){
            return quake;
        }
        else if (arg.equals("magic_protection")){
            return magic_protection;
        }
        else {
            return error;
        }
    } 

    public static final List<String> enchantmentArgs = List.of("radiant", "thunder_aspect", "decay", "recoil", "two_handed", "quake", "magic_protection");

    //All enchantments go here
    public static final Enchantment error = new EnchatmentWrapper("error", "Error", 1);
    public static final Enchantment radiant = new EnchatmentWrapper("radiant", "Radiant", 3);

    public static final Enchantment thunder_aspect = new EnchatmentWrapper("thunder_aspect", "Thunder Aspect", 5);

    public static final Enchantment decay = new EnchatmentWrapper("decay", "Decay", 40);

    public static final Enchantment recoil = new EnchatmentWrapper("recoil", "Recoil", 10);

    public static final Enchantment two_handed = new EnchatmentWrapper("two_handed", "Two Handed", 1, true);

    public static final Enchantment quake = new EnchatmentWrapper("quake", "Quake", 3);

    public static final Enchantment magic_protection = new EnchatmentWrapper("magic_protection", "Magic Protection", 3);


}
