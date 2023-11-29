package pl.mn.mncustomenchants.CustomEnchantments;

import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getLogger;

public class EnchantmentRegister {




    //register code
    public static void register(Enchantment enchantment) {
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchantment);

        if (!registered){
            registerEnchantments(enchantment);
        }
    }

    public static void registerEnchantments (Enchantment enchantment){
        boolean registered = true;
        try {

            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);

        }   catch (Exception e){
            registered = false;
        }

    }
}
