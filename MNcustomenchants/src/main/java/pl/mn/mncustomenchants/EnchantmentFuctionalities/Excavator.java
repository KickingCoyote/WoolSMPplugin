package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import io.papermc.paper.math.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import org.joml.Vector2d;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;
import pl.mn.mncustomenchants.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Excavator implements Listener {

    public Excavator(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }


    @EventHandler
    public void OnBlockMine (BlockBreakEvent event){

        Enchantment ench = CustomEnchantments.excavator;

        if (!EntityClassifications.isPlayerWithEnch(ench, event.getPlayer(), EquipmentSlot.HAND)){ return; }

        if (event.getPlayer().isSneaking()) { return; }
        //gets the direction of the block face
        Vector v = event.getPlayer().getTargetBlockFace(10).getDirection();

        //list of relative coordinates, based on that the direction is in the Z-axis
       List<Vector> relatives = List.of(
               new Vector(1, 1, 0),
               new Vector(0, 1, 0),
               new Vector(1, 0, 0),
               new Vector(-1,-1,0),
               new Vector(-1,0, 0),
               new Vector(0, -1,0),
               new Vector(-1,1, 0),
               new Vector(1, -1,0)
       );

       for (Vector relative : relatives){

            //if the direction is x/y flip the relatives
           if (v.getX() != 0){
                relative.setZ(relative.getX());
                relative.setX(0);
           }
           else if (v.getY() != 0){
               relative.setZ(relative.getY());
               relative.setY(0);
           }

           //the relative block
           Block b = event.getPlayer().getWorld().getBlockAt(event.getBlock().getLocation().add(relative));

           //Skip blocks that have hardness below 0 or above 4
           if (b.getType().getHardness() >= 4 || b.getType().getHardness() < 0){continue;}

           //break naturally (works with silk touch)
           b.breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
       }



    }
}
