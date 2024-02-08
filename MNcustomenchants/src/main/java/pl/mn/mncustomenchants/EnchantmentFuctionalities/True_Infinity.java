package pl.mn.mncustomenchants.EnchantmentFuctionalities;


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

        if (!EntityUtils.isPlayerWithEnch(CustomEnchantments.true_infinity, event.getEntity(), EquipmentSlot.HAND)) { return; }

        event.setConsumeItem(false);

    }


    @EventHandler
    public void OnProjectile (ProjectileLaunchEvent event){

        if (!(event.getEntity().getShooter() instanceof Player)){ return; }




        Player player = (Player) event.getEntity().getShooter();

        if(!EntityUtils.isPlayerWithEnch(CustomEnchantments.true_infinity, player, EquipmentSlot.HAND)){ return; }


        //Infinity for crossbows part 2
        //Makes it so that arrows cant be duped
        if (player.getInventory().getItemInMainHand().getType() == Material.CROSSBOW && event.getEntity() instanceof Arrow){
            ((Arrow) event.getEntity()).setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        }



        //Infinity for throwables
        if (event.getEntity() instanceof Snowball || event.getEntity() instanceof Egg || event.getEntity() instanceof EnderPearl){


            ItemStack itemStack = player.getInventory().getItemInMainHand();

            //Adds back the snowball, for some reason it must be added not set
            player.getInventory().setItemInMainHand(itemStack.add());

            //sets it back to previous amount
            player.getInventory().getItemInMainHand().setAmount(itemStack.getAmount() -1 );

            //resets visual bug with item amount
            player.getInventory().setItemInMainHand(player.getInventory().getItemInMainHand());



        }
        //Infinity for potions
        else if (event.getEntity() instanceof ThrownPotion){

            ItemStack itemStack = player.getInventory().getItemInMainHand();
            itemStack.setAmount(itemStack.getAmount() + 1);

            player.getInventory().setItemInMainHand(itemStack);
            player.getInventory().getItemInMainHand().subtract(itemStack.getAmount() -1);

        }




    }


    //Infinite Consumables
    @EventHandler
    public void OnConsumable (PlayerItemConsumeEvent event){
        if (!EntityUtils.isPlayerWithEnch(CustomEnchantments.true_infinity, event.getPlayer(), EquipmentSlot.HAND)){ return; }

        event.setReplacement(event.getItem());

    }





}
