package pl.mn.mncustomenchants.EntityMethods;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.ItemMethods.AttributeType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.main;

public class Projectiles implements Listener {

    public Projectiles (){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }


    @EventHandler
    public void OnProjectileLaunch (ProjectileLaunchEvent event){

        if (!(event.getEntity().getShooter() instanceof Player)) { return; }

        Projectile projectile = event.getEntity();
        Player shooter = (Player) event.getEntity().getShooter();

        event.getEntity().setVelocity(projectile.getVelocity().multiply(ItemUtils.getPlayerAttribute(shooter, AttributeType.PROJECTILE_SPEED)));

        if ((projectile instanceof Snowball || projectile instanceof Egg || projectile instanceof Trident || projectile instanceof ThrownPotion) && ItemUtils.getPlayerAttribute(shooter, AttributeType.THROW_RATE) != 0) {
            shooter.setCooldown(shooter.getInventory().getItemInMainHand().getType(), (int) Math.round(20 / ItemUtils.getPlayerAttribute(shooter, AttributeType.THROW_RATE)));
        }

        //tridents
        if (projectile instanceof Trident && ((Trident) projectile).getLoyaltyLevel() == 0){
            ((Trident) projectile).setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);

            //Sets the despawn time to 10 sek.
            ((Trident) projectile).setLifetimeTicks(1000);


            ItemStack trident = shooter.getInventory().getItemInMainHand();

            shooter.getInventory().setItemInMainHand(trident);
        }

    }

}