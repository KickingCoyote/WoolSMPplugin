package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;

public class Recoil implements Listener {

    Plugin plugin;

    public Recoil(Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void OnProjectileFired(ProjectileLaunchEvent event){

        if (!(event.getEntity().getShooter() instanceof Player)){
            return;
        }

        Player player = (Player) event.getEntity().getShooter();

        if (!player.isSneaking()){


            if (EntityUtils.isPlayerWithEnch(CustomEnchantments.recoil, player, EquipmentSlot.HAND)){



                double enchLvl = player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchantments.recoil);

                Vector vector = event.getEntity().getVelocity().normalize().multiply(-0.5 * Math.sqrt(enchLvl));


                vector.setY(Math.max(0.1, vector.getY()));


                player.setVelocity(vector);


            }
        }

    }

}
