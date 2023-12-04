package pl.mn.mncustomenchants.CustomEnchantments;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;

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
            case "true_infinity":
                return true_infinity;
            case "regeneration":
                return regeneration;
            case "arcane_strike":
                return arcane_strike;
            case "ice_aspect":
                return ice_aspect;
            case "curse_of_corruption":
                return curse_of_corruption;
            default:
                return error;
        }
    } 

    public static final List<String> enchantmentArgs = List.of(
            "radiant",
            "thunder_aspect",
            "decay", "recoil",
            "two_handed",
            "quake", "magic_protection",
            "melee_protection",
            "true_infinity",
            "regeneration",
            "arcane_strike",
            "ice_aspect",
            "curse_of_corruption"
    );

    //All enchantments go here
    public static final Enchantment error = new EnchatmentWrapper("error", "Error", 1);
    public static final Enchantment radiant = new EnchatmentWrapper("radiant", "Radiant", 2);

    public static final Enchantment thunder_aspect = new EnchatmentWrapper("thunder_aspect", "Thunder Aspect", 20);

    public static final Enchantment decay = new EnchatmentWrapper("decay", "Decay", 40);

    public static final Enchantment recoil = new EnchatmentWrapper("recoil", "Recoil", 2);

    public static final Enchantment two_handed = new EnchatmentWrapper("two_handed", "Two Handed", 1, true);

    public static final Enchantment quake = new EnchatmentWrapper("quake", "Quake", 2);

    public static final Enchantment magic_protection = new EnchatmentWrapper("magic_protection", "Magic Protection", 2);

    public static final Enchantment melee_protection = new EnchatmentWrapper("melee_protection", "Melee Protection", 2);

    public static final Enchantment true_infinity = new EnchatmentWrapper("true_infinity", "Infinity", 1);

    public static final Enchantment regeneration = new EnchatmentWrapper("regeneration", "Regeneration", 2);

    public static final Enchantment ice_aspect = new EnchatmentWrapper("ice_aspect", "Ice Aspect", 2);

    public static final Enchantment curse_of_corruption = new EnchatmentWrapper("curse_of_corruption", "Curse of Corruption", 1, true);

    //SPELLS
    public static final Enchantment arcane_strike = new EnchatmentWrapper("arcane_strike", "Arcane Strike", 2);


}
