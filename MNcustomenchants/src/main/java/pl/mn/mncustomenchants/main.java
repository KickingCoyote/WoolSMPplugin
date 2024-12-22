package pl.mn.mncustomenchants;

//import jdk.jpackage.internal.Log;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mn.mncustomenchants.Abilities.AbilityManager;
import pl.mn.mncustomenchants.Bosses.Hellborn_Servant;
import pl.mn.mncustomenchants.Commands.EditItemV2;
import pl.mn.mncustomenchants.Commands.TabCompletion;
import pl.mn.mncustomenchants.Commands.UpdateItem;
import pl.mn.mncustomenchants.CustomDamage.CustomDamage;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantment;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.*;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.EnchantmentSpells.Advancing_Shadows;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.EnchantmentSpells.Arcane_Strike;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.EnchantmentSpells.Dragonblade;
import pl.mn.mncustomenchants.EntityMethods.Classifications.PlayerUpdates;
import pl.mn.mncustomenchants.EntityMethods.Projectiles;
import pl.mn.mncustomenchants.Graves.SpawnGrave;
import pl.mn.mncustomenchants.ItemMethods.Attributes.JumpHeight;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;

public final class main extends JavaPlugin implements CommandExecutor {

    //THIS IS THE REAL VERSION
    //ONLY THIS VERSION APPEARS ON GIT

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

        new Dragonblade();
        new Advancing_Shadows();

        //Attributes
        new JumpHeight();

        //Bosses
        new Hellborn_Servant();

        //misc
        //new InventoryShulkers();
        new Projectiles();
        new AbilityManager();
        new SpawnGrave();

        LivingEntity e;

        //CommandStuff
        getCommand("customenchant").setTabCompleter(new TabCompletion());
        getCommand("EditItemV2").setTabCompleter(new TabCompletion());

        //getCommand("EditItemV2").register(EditItemV2)
        getCommand("EditItemV2").setExecutor(new EditItemV2());
        getCommand("UpdateItem").setExecutor(new UpdateItem());

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //EntityClassifications.activeEffects.clear();

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(label.equalsIgnoreCase("customenchant")){
            if(!(sender instanceof Player player))
                return true;

            int lvl = Integer.parseInt(args[1]);

            CustomEnchantment enchantment = CustomEnchantment.valueOf(args[0]);

            ItemStack item = player.getInventory().getItemInMainHand();

            CustomEnchantment.removeEnchantment(item, enchantment);
            if (lvl != 0){
                CustomEnchantment.addEnchantment(item, enchantment, lvl);
            }

            ItemUtils.UpdateLore(item);

        }

        return true;

    }


    public static main getInstance(){
        return getPlugin(main.class);
    }

}
