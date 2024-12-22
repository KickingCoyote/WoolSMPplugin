package pl.mn.mncustomenchants.EntityMethods;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import pl.mn.mncustomenchants.ItemMethods.AttributeType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.main;

public class Projectiles implements Listener {

    public Projectiles (){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }





    @EventHandler
    public void OnProjectileLaunch (PlayerLaunchProjectileEvent event){




        Projectile projectile = event.getProjectile();
        Player shooter = event.getPlayer();

        projectile.setVelocity(projectile.getVelocity().multiply(ItemUtils.getEntityAttribute(shooter, AttributeType.PROJECTILE_SPEED)));




        if ((projectile instanceof Snowball || projectile instanceof Egg || projectile instanceof Trident || projectile instanceof ThrownPotion) && ItemUtils.getEntityAttribute(shooter, AttributeType.THROW_RATE) != 0) {

            shooter.setCooldown(event.getItemStack().getType(), (int) Math.round(20 / ItemUtils.getEntityAttribute(shooter, AttributeType.THROW_RATE)));

        }

        //tridents
        if (projectile instanceof Trident && ((Trident) projectile).getLoyaltyLevel() == 0){
            ((Trident) projectile).setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);

            //Sets the despawn time to 10 sek.
            ((Trident) projectile).setLifetimeTicks(1000);


            ItemStack trident = event.getItemStack();

            shooter.getInventory().setItemInMainHand(trident);
        }

    }



}
