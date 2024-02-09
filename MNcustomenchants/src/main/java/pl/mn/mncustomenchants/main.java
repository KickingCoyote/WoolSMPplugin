package pl.mn.mncustomenchants;

//import jdk.jpackage.internal.Log;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mn.mncustomenchants.Commands.EditItemV2;
import pl.mn.mncustomenchants.Commands.TabCompletion;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.CustomEnchantments.EnchantmentRegister;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.*;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.EnchantmentSpells.Arcane_Strike;
import pl.mn.mncustomenchants.EntityMethods.Classifications.PlayerUpdates;
import pl.mn.mncustomenchants.EntityMethods.Projectiles;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;

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
        new Recoil(this);
        new Radiant(this);
        new True_Infinity(this);
        new Arcane_Strike(this);
        new Excavator();
        new Quake();
        new Sustenance();
        new Teleportation();
        new Rocket_Crossbow();



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
