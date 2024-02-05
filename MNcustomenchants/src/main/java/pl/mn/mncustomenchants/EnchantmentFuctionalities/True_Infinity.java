package pl.mn.mncustomenchants.EnchantmentFuctionalities;


import com.destroystokyo.paper.event.entity.PlayerNaturallySpawnCreaturesEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;

public class True_Infinity implements Listener {

    Plugin plugin;
    Projectile projectile;
    public True_Infinity (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void OnProjectile (ProjectileLaunchEvent event){

        if (!(event.getEntity().getShooter() instanceof Player)){ return; }


        Player player = (Player) event.getEntity().getShooter();

        if(!EntityClassifications.isPlayerWithEnch(CustomEnchantments.true_infinity, player, EquipmentSlot.HAND)){ return; }



        if (player.getInventory().getItemInMainHand().getType() == Material.CROSSBOW && event.getEntity() instanceof Arrow && ((Arrow) event.getEntity()).getItemStack().getType() == Material.ARROW){

            player.getInventory().addItem(new ItemStack(Material.ARROW));

            ((Arrow)event.getEntity()).setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        }


        //Infinity for throwables
        else if (event.getEntity() instanceof Snowball || event.getEntity() instanceof Egg || event.getEntity() instanceof EnderPearl){


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
        if (!EntityClassifications.isPlayerWithEnch(CustomEnchantments.true_infinity, event.getPlayer(), EquipmentSlot.HAND)){ return; }

        event.setReplacement(event.getItem());

    }





}
