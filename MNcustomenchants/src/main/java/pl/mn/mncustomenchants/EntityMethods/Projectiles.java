package pl.mn.mncustomenchants.EntityMethods;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.ItemMethods.AttributeType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Projectiles implements Listener {

    public Projectiles (){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }





    @EventHandler
    public void OnProjectileLaunch (PlayerLaunchProjectileEvent event){




        Projectile projectile = event.getProjectile();
        Player shooter = event.getPlayer();

        projectile.setVelocity(projectile.getVelocity().multiply(ItemUtils.getPlayerAttribute(shooter, AttributeType.PROJECTILE_SPEED)));




        if ((projectile instanceof Snowball || projectile instanceof Egg || projectile instanceof Trident || projectile instanceof ThrownPotion) && ItemUtils.getPlayerAttribute(shooter, AttributeType.THROW_RATE) != 0) {

            shooter.setCooldown(event.getItemStack().getType(), (int) Math.round(20 / ItemUtils.getPlayerAttribute(shooter, AttributeType.THROW_RATE)));

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
