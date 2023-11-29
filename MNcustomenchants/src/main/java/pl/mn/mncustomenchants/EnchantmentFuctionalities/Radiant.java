package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Radiant {

    Plugin plugin;
    public Radiant (Plugin plugin){
        this.plugin = plugin;
    }

    public static void ApplyGlowIfGlow (Player player){

        /*

        if (EntityClassifications.isPlayerWithEnch(CustomEnchantments.radiant, player, EquipmentSlot.HAND)){

            int enchLvl = player.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.radiant);

            for (Player p : Bukkit.getOnlinePlayers()){

                boolean b = p.isGlowing();

                if (EntityClassifications.getDistance(player, p) < 10 * enchLvl){
                    p.setGlowing(true);
                }else {
                    p.setGlowing(b);
                }

            }
            for (World world : Bukkit.getServer().getWorlds()){
                for (LivingEntity entity : world.getLivingEntities()){
                    boolean b = entity.isGlowing();

                    if (EntityClassifications.getDistance(player, entity) < 10 * enchLvl){
                        entity.setGlowing(true);
                    }else {
                        entity.setGlowing(b);
                    }
                }
            }

        }

        */
    }


}
