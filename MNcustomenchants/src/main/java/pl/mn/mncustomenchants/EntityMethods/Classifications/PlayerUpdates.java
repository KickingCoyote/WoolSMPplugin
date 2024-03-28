package pl.mn.mncustomenchants.EntityMethods.Classifications;

import io.papermc.paper.event.player.PlayerArmSwingEvent;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.units.qual.A;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.Curse_of_Corruption;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.Radiant;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.Regeneration;
import pl.mn.mncustomenchants.EnchantmentFuctionalities.Two_Handed;
import pl.mn.mncustomenchants.ItemMethods.VanillaModifications;

import java.util.List;

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
            //event.getPlayer().getInventory().setItem(event.getSlot(), ItemStack.empty());
        }

    }

    @EventHandler
    public void swingEvent(PlayerArmSwingEvent event){

        event.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
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

        VanillaModifications.Enchanting(event);
        VanillaModifications.Anvil(event);

    }


    @EventHandler
    public void OnInventoryClick (InventoryClickEvent event){
        VanillaModifications.vanillaToCustomAttributes(event.getCursor());
    }
    @EventHandler
    public void OnPickUpItem (PlayerAttemptPickupItemEvent event){
        VanillaModifications.vanillaToCustomAttributes(event.getItem().getItemStack());
    }


    @EventHandler
    public void OnDeath (PlayerDeathEvent event){


        //TODO: Check if this is needed
        //resets modifiers on death
        List<Attribute> attrs = List.of(
                Attribute.GENERIC_MOVEMENT_SPEED,
                Attribute.GENERIC_ARMOR,
                Attribute.GENERIC_KNOCKBACK_RESISTANCE,
                Attribute.GENERIC_ATTACK_DAMAGE
        );

        for (Attribute attr : attrs){
            for (AttributeModifier a : event.getPlayer().getAttribute(attr).getModifiers()){
                event.getPlayer().getAttribute(attr).removeModifier(a);
            }
        }


    }




    public static void inventoryUpdate (Player player){
        Two_Handed.CheckTwoHanded(player);
        Curse_of_Corruption.CheckCoC(player);

        Radiant.ApplyGlowIfGlow(player);
        Regeneration.CheckRegeneration(player);

        //VanillaModifications.NonCustomItemsWithEnchants(player);

        VanillaModifications.customToVanillaAttributes(player);


    }




}
