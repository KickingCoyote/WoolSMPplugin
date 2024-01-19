package pl.mn.mncustomenchants;

//import jdk.jpackage.internal.Log;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
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


import java.awt.*;
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




            AddLore(item, ench, lvl);

        }

        return true;

    }
    public void AddLore(ItemStack item, Enchantment ench, int lvl){
        ItemMeta meta = item.getItemMeta();
        List<Component> lore;
        List<Component> oldLore = new ArrayList<Component>();
        lore = new ArrayList<Component>();
        List<String> oldLoreString = new ArrayList<String>();

        if (meta.hasLore())
            oldLore = meta.lore();


        Component component;
        TextColor textColor;
        String lvlToSting = String.valueOf(lvl);

        if (ench.isCursed()){
            textColor = TextColor.color(255, 85 ,85);
        }else {
            textColor = TextColor.color(169, 169 ,169);
        }
        if (ench.getMaxLevel() == 1){
            lvlToSting = "";
        }


        component = Component.text(ench.getName() + " " + lvlToSting, textColor);



        assert oldLore != null;
        for (Component com : oldLore){
            oldLoreString.add(com.toString());
        }
        for (int i = 0; i < oldLoreString.size(); i++){
            if (oldLoreString.get(i).contains(ench.getName())){
                oldLore.remove(i);
            }
        }


        if(oldLore.contains(Component.text("")))
            oldLore.remove(component);

        if(lvl != 0){
            lore.add(component);
        }

        lore.addAll(oldLore);


        meta.lore(lore);


        item.setItemMeta(meta);

    }

    public static main getInstance(){
        return getPlugin(main.class);
    }

}
