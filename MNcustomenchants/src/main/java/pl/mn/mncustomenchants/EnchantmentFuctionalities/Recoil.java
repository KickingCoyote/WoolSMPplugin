package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.Spells.SpellManager;
import pl.mn.mncustomenchants.Spells.Spells.FireballSpell;

public class Recoil implements Listener {

    Plugin plugin;

    public Recoil(Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void OnProjectileFired(PlayerLaunchProjectileEvent event){

        //if (event.getProjectile() instanceof Fireball){return;}
        //SpellManager.castSpell(new FireballSpell(event.getPlayer(), 10, event.getPlayer().getEyeLocation(), event.getPlayer().getLocation().getDirection(), 1, 20, 10, 1));

        if (!event.getPlayer().isSneaking()){
            if (EntityUtils.isPlayerWithEnch(CustomEnchantments.recoil, event.getPlayer(), EquipmentSlot.HAND)){



                double enchLvl = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchantments.recoil);

                Vector vector = event.getProjectile().getVelocity().normalize().multiply(-0.5 * Math.sqrt(enchLvl));


                vector.setY(Math.max(0.1, vector.getY()));


                event.getPlayer().setVelocity(vector);


            }
        }

    }

}
