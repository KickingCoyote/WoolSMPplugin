package pl.mn.mncustomenchants.EnchantmentFuctionalities;


import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
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
        if (event.getEntity().getShooter() instanceof Player){

            Player player = (Player) event.getEntity().getShooter();

            if(EntityClassifications.isPlayerWithEnch(CustomEnchantments.true_infinity, player, EquipmentSlot.HAND)){

                if (player.getInventory().getItemInMainHand().getType() == Material.CROSSBOW){
                    projectile = event.getEntity();
                    player.getInventory().addItem(new ItemStack(Material.ARROW));
                    ((Arrow)projectile).setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                    //((Arrow)projectile).setLifetimeTicks(50);
                }


                //Infinity for throwables currently not working
                /*
                else if (player.getInventory().getItemInMainHand().getType() == Material.SNOWBALL){

                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    Bukkit.getPlayer("MN_128").sendMessage(itemStack.toString());

                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            player.getInventory().setItem(EquipmentSlot.HAND, itemStack);
                        }
                    }, 10);

                }  */



            }
        }
    }

    /*@EventHandler
    public void OnPickUp(PlayerPickupArrowEvent event){
        if (event.getArrow() == projectile){
            event.getArrow().setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            event.getArrow().remove();
        }


    }
    */

}
