package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.main;

import java.util.ArrayList;
import java.util.List;

public class Rocket_Crossbow implements Listener {

    public Rocket_Crossbow(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }


    //Converts rockets in crossbows to arrows if the crossbow doesn't have rocket_crossbow enchantment and the reverse if it does
    @EventHandler
    public void OnCrossbowLoad (EntityLoadCrossbowEvent event){


        ItemStack loadItem;
        List<ItemStack> loadItems = new ArrayList<>();


        int enchLvl = EntityUtils.itemEnchLvl(CustomEnchantments.rocket_crossbow, event.getCrossbow());

        if (enchLvl != 0)
        {

            FireworkEffect.Type type;

            if(enchLvl == 1){
                type = FireworkEffect.Type.BALL;
            } else {
                type = FireworkEffect.Type.BALL_LARGE;
            }



            loadItem = new ItemStack(Material.FIREWORK_ROCKET, 1);
            FireworkMeta fireworkMeta = (FireworkMeta) loadItem.getItemMeta();

            fireworkMeta.setPower(1);
            fireworkMeta.addEffect(FireworkEffect.builder().with(type).withColor(Color.SILVER).build());

            loadItem.setItemMeta(fireworkMeta);

        } else {
            loadItem = new ItemStack(Material.ARROW, 1);
        }

        loadItems.add(loadItem);
        if (event.getCrossbow().getEnchantmentLevel(Enchantment.MULTISHOT) != 0){
            loadItems.add(loadItem);
            loadItems.add(loadItem);
        }

        Bukkit.getScheduler().runTaskLater(main.getInstance(), () -> {

            CrossbowMeta crossbowMeta = (CrossbowMeta)event.getCrossbow().getItemMeta();

            crossbowMeta.setChargedProjectiles(loadItems);

            event.getCrossbow().setItemMeta(crossbowMeta);


        }, 1);



    }


    @EventHandler
    private void onPlayerProjectile (PlayerLaunchProjectileEvent event){

        launchFirework(event.getPlayer(), event.getProjectile(), event.getItemStack());

    }

    @EventHandler
    private  void onEntityBow (EntityShootBowEvent event){

        //if(!(event.getEntity() instanceof Player)) { return; }


        launchFirework( event.getEntity(), (Projectile) event.getProjectile(), event.getBow());

    }

    private void launchFirework (LivingEntity entity, Projectile projectile, ItemStack itemStack){

        int enchLvl = EntityUtils.itemEnchLvl(CustomEnchantments.rocket_crossbow, itemStack);

        if (enchLvl == 0){ return; }


        FireworkEffect.Type type;

        if(enchLvl == 1){
            type = FireworkEffect.Type.BALL;
        } else {
            type = FireworkEffect.Type.BALL_LARGE;
        }


        Firework firework = (Firework) entity.getWorld().spawnEntity(entity.getEyeLocation().subtract(0, 0.25, 0), EntityType.FIREWORK);

        firework.setVelocity(projectile.getVelocity());

        firework.setShotAtAngle(true);

        firework.setShooter(entity);

        FireworkMeta fireworkMeta = firework.getFireworkMeta();


        fireworkMeta.setPower(1);
        fireworkMeta.addEffect(FireworkEffect.builder().with(type).withColor(Color.SILVER).build());
        firework.setFireworkMeta(fireworkMeta);

        projectile.remove();

    }




}
