package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.EntityMethods.Projectiles;
import pl.mn.mncustomenchants.ItemMethods.AttributeType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.main;

public class Teleportation implements Listener {

    public Teleportation(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }

    @EventHandler
    public void playerLaunchProjectile(PlayerLaunchProjectileEvent event) {


        if(EntityUtils.itemEnchLvl(CustomEnchantments.teleportation, event.getItemStack()) == 0){ return; }

        double distance = 5 * EntityUtils.itemEnchLvl(CustomEnchantments.teleportation, event.getItemStack());



        int cooldown = ItemUtils.getPlayerAttribute(event.getPlayer(), AttributeType.THROW_RATE) > 0 ? (int) Math.round(20 / ItemUtils.getPlayerAttribute(event.getPlayer(), AttributeType.THROW_RATE)) : 0;


        Projectile projectile = event.getProjectile();
        Player shooter = event.getPlayer();


        Location location = projectile.getLocation().add(projectile.getLocation().getDirection().multiply(-distance).multiply(new Vector(1, 1 , -1)));

        Location landingLocation = shooter.rayTraceBlocks(distance, FluidCollisionMode.NEVER) == null ? location : shooter.rayTraceBlocks(distance, FluidCollisionMode.NEVER).getHitBlock().getLocation().add(shooter.rayTraceBlocks(distance, FluidCollisionMode.NEVER).getHitBlockFace().getDirection().multiply(1.5));

        landingLocation.setDirection(shooter.getLocation().getDirection());

        if (shooter.getCooldown(event.getItemStack().getType()) == 0){
            shooter.teleport(landingLocation);
            shooter.setCooldown(event.getItemStack().getType(), cooldown);
        }



        if (EntityUtils.itemEnchLvl(CustomEnchantments.true_infinity, event.getItemStack()) != 0){
            event.getItemStack().subtract();
        }

        event.getProjectile().remove();
        event.setCancelled(true);

    }


}
