package pl.mn.mncustomenchants;

//import jdk.jpackage.internal.Log;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.StyleBuilderApplicable;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mn.mncustomenchants.Commands.EditItemV2;
import pl.mn.mncustomenchants.Commands.TabCompletion;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.CustomEnchantments.EnchantmentRegister;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.*;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.EnchantmentSpells.Arcane_Strike;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;
import pl.mn.mncustomenchants.EntityMethods.Classifications.PlayerUpdates;
import pl.mn.mncustomenchants.EntityMethods.Projectiles;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;


import java.awt.*;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.logging.Logger;
public final class main extends JavaPlugin implements CommandExecutor {

    //THIS IS THE REAL VERSION
    //ONLY THIS VERSION APEARS ON GIT

    @Override
    public void onEnable() {


        //effects list
        //EntityClassifications.activeEffects = new ArrayList<>();

        //Updates
        new PlayerUpdates(this);
        new CustomDamage(this);

        //Register Enchantment Functionality
        new Thunder_Aspect(this);
        new Decay(this);
        new Recoil(this);
        new Radiant(this);
        new Quake(this);
        new True_Infinity(this);
        new Regeneration(this);
        new Arcane_Strike(this);
        new Ice_Aspect(this);
        new Excavator();
        new True_Fire_Aspect();
        new Sustenance();

        //handles projectiles
        new Projectiles();


        //CommandStuff
        getCommand("customenchant").setTabCompleter(new TabCompletion());
        getCommand("EditItemV2").setTabCompleter(new TabCompletion());

        //getCommand("EditItemV2").register(EditItemV2)
        getCommand("EditItemV2").setExecutor(new EditItemV2());

        //Register all enchantments
        for (String s : CustomEnchantments.enchantmentArgs){
            RegEnch(CustomEnchantments.valueOf(s));
        }

    }

    public void RegEnch (Enchantment enchantment){
        EnchantmentRegister.register(enchantment);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //EntityClassifications.activeEffects.clear();

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(label.equalsIgnoreCase("customenchant")){
            if(!(sender instanceof Player))
                return true;

            Player player = (Player) sender;
            int lvl = Integer.parseInt(args[1]);

            Enchantment ench = CustomEnchantments.valueOf(args[0]);



            if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(ench)){
                player.getInventory().getItemInMainHand().getItemMeta().removeEnchant(ench);
            }

            ItemStack item = player.getInventory().getItemInMainHand();

            item.addUnsafeEnchantment(ench, lvl);



            ItemUtils.UpdateLore(item);

        }

        return true;

    }


    public static main getInstance(){
        return getPlugin(main.class);
    }

}
