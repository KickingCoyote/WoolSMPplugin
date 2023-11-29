package pl.mn.mncustomenchants.EntityMethods.Classifications;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.Radiant;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.Two_Handed;

public class PlayerUpdates implements Listener {

    Plugin plugin;

    public PlayerUpdates (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }


    @EventHandler
    public void OnPlayerJoin (PlayerJoinEvent event){
        inventoryUpdate(event.getPlayer());

    }
    @EventHandler
    public void OnPlayerDrop (PlayerDropItemEvent event){
        inventoryUpdate(event.getPlayer());

    }
    @EventHandler
    public void OnPlayerPickUp (PlayerPickItemEvent event){
        inventoryUpdate(event.getPlayer());

    }
    @EventHandler
    public void OnPlayerInventory (PlayerInventorySlotChangeEvent event){
        inventoryUpdate(event.getPlayer());

    }
    @EventHandler
    public void OnPlayerMainHandSwap (PlayerItemHeldEvent event){

        BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                inventoryUpdate(event.getPlayer());
            }
        }, 1);


    }


    public static void inventoryUpdate (Player player){
        Two_Handed.CheckTwoHanded(player);
        Radiant.ApplyGlowIfGlow(player);

    }


}
