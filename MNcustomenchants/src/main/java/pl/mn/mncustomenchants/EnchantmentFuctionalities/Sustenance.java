package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.main;


//Handles both sustenace and Curse of Anemia
public class Sustenance implements Listener {

    public Sustenance(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }


    @EventHandler
    public void OnHeal(EntityRegainHealthEvent event){

        if (!(event.getEntity() instanceof Player)) { return; }

        int enchLvl = EntityUtils.combinedEnchantLvl((Player) event.getEntity(), CustomEnchantments.sustenance);
        int curseLvl = EntityUtils.combinedEnchantLvl((Player) event.getEntity(), CustomEnchantments.curse_of_anemia);

        event.setAmount(event.getAmount() * (1 + (0.1 * (enchLvl - curseLvl))));

    }

}
