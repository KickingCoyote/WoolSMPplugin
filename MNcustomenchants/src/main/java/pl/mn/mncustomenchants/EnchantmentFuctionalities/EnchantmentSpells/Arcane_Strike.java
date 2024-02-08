package pl.mn.mncustomenchants.EnchantmentFuctionalities.EnchantmentSpells;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;

public class Arcane_Strike implements Listener {

    Plugin plugin;

    public Arcane_Strike (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    public void OnMeleeHit (EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player && EntityUtils.isPlayerWithEnch(CustomEnchantments.arcane_strike, (Player)event.getDamager(), EquipmentSlot.HAND)){







        }
    }


}
