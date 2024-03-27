package pl.mn.mncustomenchants.Bosses;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.mn.mncustomenchants.main;

public class Hellborn_Servant implements Listener {


    public Hellborn_Servant(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){

        if (!event.getBlockPlaced().getType().equals(Material.BEACON)) { return; }

        if (!(event.getBlockPlaced().getLocation().add(0, -1 ,0).getBlock().getType() == Material.CRYING_OBSIDIAN)){
            return;
        }

        event.getBlock().setType(Material.AIR);
        event.getPlayer().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.SHEEP);

    }


}
