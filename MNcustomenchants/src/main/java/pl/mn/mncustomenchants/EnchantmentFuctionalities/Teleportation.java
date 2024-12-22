package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantment;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.ItemMethods.AttributeType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.main;

public class Teleportation implements Listener {

    public Teleportation(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }

    @EventHandler
    public void playerLaunchProjectile(PlayerLaunchProjectileEvent event) {


        if(EntityUtils.itemEnchantmentLvl(CustomEnchantment.teleportation, event.getItemStack()) == 0){ return; }

        double distance = 5 * EntityUtils.itemEnchantmentLvl(CustomEnchantment.teleportation, event.getItemStack());



        int cooldown = ItemUtils.getEntityAttribute(event.getPlayer(), AttributeType.THROW_RATE) > 0 ? (int) Math.round(20 / ItemUtils.getEntityAttribute(event.getPlayer(), AttributeType.THROW_RATE)) : 0;


        Projectile projectile = event.getProjectile();
        Player shooter = event.getPlayer();




        if (shooter.getCooldown(event.getItemStack().getType()) == 0){

            Location location = projectile.getLocation().add(projectile.getLocation().getDirection().multiply(-distance).multiply(new Vector(1, 1 , -1)));

            Location landingLocation = shooter.rayTraceBlocks(distance, FluidCollisionMode.NEVER) == null ? location : shooter.rayTraceBlocks(distance, FluidCollisionMode.NEVER).getHitBlock().getLocation().add(shooter.rayTraceBlocks(distance, FluidCollisionMode.NEVER).getHitBlockFace().getDirection().multiply(1.5));

            landingLocation.setDirection(shooter.getLocation().getDirection());

            shooter.teleport(landingLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);


            //Temporarily sets attack speed to 100 to counter the fact that teleporting resets attack cool down to 0 (probably bug)
            if(shooter.getAttackCooldown() == 1){
                Bukkit.getScheduler().runTaskLater(main.getInstance(), () -> shooter.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100), 1);
            }


            shooter.setCooldown(event.getItemStack().getType(), cooldown);
        }



        if (EntityUtils.itemEnchantmentLvl(CustomEnchantment.true_infinity, event.getItemStack()) != 0){
            event.getItemStack().subtract();
        }

        event.getProjectile().remove();
        event.setCancelled(true);

    }




}
