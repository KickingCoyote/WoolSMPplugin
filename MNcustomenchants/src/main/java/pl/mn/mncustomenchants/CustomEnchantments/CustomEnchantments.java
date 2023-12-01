package pl.mn.mncustomenchants.CustomEnchantments;

import org.bukkit.enchantments.Enchantment;
import java.util.List;

public class CustomEnchantments {

    public static Enchantment valueOf(String arg){
        switch (arg) {
            case "radiant":
                return radiant;
            case "thunder_aspect":
                return thunder_aspect;
            case "decay":
                return decay;
            case "recoil":
                return recoil;
            case "two_handed":
                return two_handed;
            case "quake":
                return quake;
            case "magic_protection":
                return magic_protection;
            case "melee_protection":
                return melee_protection;
            default:
                return error;
        }
    } 

    public static final List<String> enchantmentArgs = List.of("radiant", "thunder_aspect", "decay", "recoil", "two_handed", "quake", "magic_protection", "melee_protection");

    //All enchantments go here
    public static final Enchantment error = new EnchatmentWrapper("error", "Error", 1);
    public static final Enchantment radiant = new EnchatmentWrapper("radiant", "Radiant", 3);

    public static final Enchantment thunder_aspect = new EnchatmentWrapper("thunder_aspect", "Thunder Aspect", 5);

    public static final Enchantment decay = new EnchatmentWrapper("decay", "Decay", 40);

    public static final Enchantment recoil = new EnchatmentWrapper("recoil", "Recoil", 10);

    public static final Enchantment two_handed = new EnchatmentWrapper("two_handed", "Two Handed", 1, true);

    public static final Enchantment quake = new EnchatmentWrapper("quake", "Quake", 3);

    public static final Enchantment magic_protection = new EnchatmentWrapper("magic_protection", "Magic Protection", 4);

    public static final Enchantment melee_protection = new EnchatmentWrapper("melee_protection", "Melee Protection", 4);


}
