package pl.mn.mncustomenchants.EnchantmentFuctionalities;


import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.A;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;

public class True_Infinity implements Listener {

    Plugin plugin;
    Projectile projectile;
    public True_Infinity (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }




    //Infinity for crossbows
    @EventHandler
    public void LoadCrossbow(EntityLoadCrossbowEvent event){

        if (EntityUtils.itemEnchLvl(CustomEnchantments.true_infinity, event.getCrossbow()) == 0) { return; }

        event.setConsumeItem(false);

    }


    @EventHandler
    public void OnProjectile (PlayerLaunchProjectileEvent event){







        Player player = event.getPlayer();

        if(EntityUtils.itemEnchLvl(CustomEnchantments.true_infinity, event.getItemStack()) == 0){ return; }


        //Infinity for crossbows part 2
        //Makes it so that arrows cant be duped
        if (player.getInventory().getItemInMainHand().getType() == Material.CROSSBOW && event.getProjectile() instanceof Arrow){
            ((Arrow) event.getProjectile()).setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        }

        //its important that it checks for main not offhand, cause if both are true main hand should be returned
        EquipmentSlot eq = event.getPlayer().getInventory().getItemInMainHand().equals(event.getItemStack()) ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;


        //Infinity for throwables
        if (event.getProjectile() instanceof Snowball || event.getProjectile() instanceof Egg || event.getProjectile() instanceof EnderPearl){


            ItemStack itemStack = player.getInventory().getItem(eq);

            //Adds back the snowball, for some reason it must be added not set
            player.getInventory().setItem(eq, itemStack.add());

            //sets it back to previous amount
            player.getInventory().getItem(eq).setAmount(itemStack.getAmount() -1 );

            //resets visual bug with item amount
            player.getInventory().setItem(eq, player.getInventory().getItem(eq));


        }
        //Infinity for potions
        else if (event.getProjectile() instanceof ThrownPotion){

            ItemStack itemStack = player.getInventory().getItem(eq);
            itemStack.setAmount(itemStack.getAmount() + 1);

            player.getInventory().setItem(eq, itemStack);
            player.getInventory().getItem(eq).subtract(itemStack.getAmount() -1);

        }




    }


    //Infinite Consumables
    @EventHandler
    public void OnConsumable (PlayerItemConsumeEvent event){
        if (EntityUtils.itemEnchLvl(CustomEnchantments.true_infinity, event.getItem()) == 0){ return; }

        event.setReplacement(event.getItem());

    }





}
