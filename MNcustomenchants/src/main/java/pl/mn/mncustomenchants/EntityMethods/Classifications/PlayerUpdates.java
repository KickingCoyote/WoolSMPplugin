package pl.mn.mncustomenchants.EntityMethods.Classifications;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.Radiant;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.Regeneration;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.Two_Handed;
import pl.mn.mncustomenchants.ItemMethods.DeleteContraband;
import pl.mn.mncustomenchants.ItemMethods.ItemClassRegister;

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

        if (event.getNewItemStack().getType() == Material.ELYTRA){
            event.getPlayer().getInventory().setItem(event.getSlot(), ItemStack.empty());
        }

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


    //Cancel Enchanting
    @EventHandler
    public void OnInventoryOpen (InventoryOpenEvent event){

        DeleteContraband.Enchanting(event);

    }


    //No Anviling For U (Except Nametags)
    @EventHandler
    public void OnInventoryClick (InventoryClickEvent event){
        DeleteContraband.Anvil(event);
    }




    public static void inventoryUpdate (Player player){
        Two_Handed.CheckTwoHanded(player);
        Radiant.ApplyGlowIfGlow(player);
        Regeneration.CheckRegeneration(player);

        DeleteContraband.NonCustomItemsWithEnchants(player);


    }




}
