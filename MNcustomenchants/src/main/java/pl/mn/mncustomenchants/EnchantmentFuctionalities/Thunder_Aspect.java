package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;

import java.util.Random;

public class Thunder_Aspect implements Listener {

    Plugin plugin;
    public Thunder_Aspect (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void OnAttacking(EntityDamageByEntityEvent event){

        Enchantment ench = CustomEnchantments.thunder_aspect;

        Random random = new Random();
        int nrn = random.nextInt(20);
        int enchLvl;


        if (EntityClassifications.isPlayerWithEnch(ench, event.getDamager(), EquipmentSlot.HAND) && ((Player)event.getDamager()).getAttackCooldown() == 1){

            enchLvl = ((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(ench);



            ((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta().addItemFlags();

            LivingEntity entity = (LivingEntity) event.getEntity();
            //if pvp
            if(event.getEntity() instanceof Player && nrn < enchLvl) {

                CustomEffects.stun(entity, 35);

            }

            //if pve
            else if (event.getEntity() instanceof Mob && nrn < enchLvl * 2){

                CustomEffects.stun(entity, 35);

            }





        }

    }


}
